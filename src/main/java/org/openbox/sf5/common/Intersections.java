package org.openbox.sf5.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Intersections {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public int checkIntersection(List<SettingsConversion> dataSettingsConversion, Settings Object) throws SQLException {

		ReturningWork<ResultSet> rowsReturningWork = new ReturningWork<ResultSet>() {

			@Override
			public ResultSet execute(Connection connection) throws SQLException {
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				try {

					// syntax changed due to H2 limitations.

					// drop tables
					preparedStatement = connection.prepareStatement(getDropTempTables());
					preparedStatement.execute();

					// fill temp tables
					preparedStatement = connection.prepareStatement(fillTempTables());
					preparedStatement.setLong(1, Object.getId());
					preparedStatement.execute();

					preparedStatement = connection.prepareStatement(getIntersectionQuery());

					resultSet = preparedStatement.executeQuery();

					// 11.08.2015, trying to remove locks
					// this removes lock from sys and temp tables.
					connection.commit();
					// 11.08.2015

					return resultSet;
				} catch (SQLException e) {
					throw e;
				}
			}
		};

		Session session = sessionFactory.openSession();

		ResultSet rs = null;
		rs = session.doReturningWork(rowsReturningWork);

		List<Integer> arrayLines = new ArrayList<Integer>();
		while (rs.next()) {

			int rowIndex = new BigDecimal(
					// 11.08.2015, there seems to be a bug in defining rows.
					rs.getLong("LineNumber")).intValueExact();

			arrayLines.add(new Integer(rowIndex));

			SettingsConversion sc = dataSettingsConversion.get(rowIndex);

			long IntersectionValue = rs.getLong("TheLineOfIntersection");

			System.out.println("table index " + rowIndex + " query row " + rs.getRow());

			sc.setTheLineOfIntersection(IntersectionValue + 1);

		}

		rs.close();
		session.close();

		// remove duplicates
		Set<Integer> hs = new HashSet<>();
		hs.addAll(arrayLines);
		arrayLines.clear();
		arrayLines.addAll(hs);
		// return rows;
		return arrayLines.size();

	}

	public static String getDropTempTables() {
		return "\n" + "DROP TABLE  CONVERSIONTABLE IF EXISTS; \n" + "DROP TABLE  ManyFrequencies IF EXISTS; \n"
				+ "DROP TABLE  IntersectionTable IF EXISTS; \n";

	}

	public static String fillTempTables() {
		return "\n" + "CREATE MEMORY TEMPORARY TABLE CONVERSIONTABLE  AS ( \n" + "SELECT \n" + "LineNumber \n"

				+ ", tp.frequency \n"

				+ ", 0 as TheLineOfIntersection \n"

		// + " into #ConversionTable \n"

				+ "	FROM SettingsConversion conv \n"

				+ "inner join Transponders tp \n"

				+ "on conv.Transponder = tp.id \n"

				+ " where parent_id = ? \n"

				+ " ); \n"

				+ "CREATE MEMORY TEMPORARY TABLE ManyFrequencies AS (  \n"

				+ "select \n" + "p1.LineNumber \n" + ", p1.frequency \n" + ", p1.TheLineOfIntersection \n"
				// + "into #ManyFrequencies \n"

				+ "from ConversionTable p1  \n"

				+ "union  \n"

				+ "select  \n" + "p2.LineNumber  \n" + ", p2.frequency + 1 \n"
				+ ", p2.TheLineOfIntersection AS  TheLineOfIntersection \n" + "from ConversionTable p2 \n"

				+ "union \n"

				+ "select \n" + "p3.LineNumber \n" + ", p3.frequency - 1 \n" + ", p3.TheLineOfIntersection \n"
				+ "from ConversionTable p3 \n"

				+ " ); \n"

				+ "CREATE MEMORY TEMPORARY TABLE IntersectionTable AS (   \n"

				+ "select \n" + "t1.LineNumber \n" + ", t1.frequency \n" + ", t2.LineNumber as TheLineOfIntersection \n"
				// + "into #IntersectionTable \n"
				+ "from ManyFrequencies t1 \n" + "inner join ManyFrequencies t2 \n"
				+ "on t1.frequency = t2.frequency \n" + "and t1.LineNumber <> t2.LineNumber \n"

				+ " ); \n";

	}

	public static String getIntersectionQuery() {

		return "\n"
				// + "DROP TABLE CONVERSIONTABLE IF EXISTS; \n"
				// + "DROP TABLE ManyFrequencies IF EXISTS; \n"
				// + "DROP TABLE IntersectionTable IF EXISTS; \n"

				// + "SET NOCOUNT ON \n"
				// +
				// "IF OBJECT_ID('tempdb..#ConversionTable') IS NOT NULL DROP
				// Table #ConversionTable \n"
				// +
				// "IF OBJECT_ID('tempdb..#ManyFrequencies') IS NOT NULL DROP
				// Table #ManyFrequencies \n"
				// +
				// "IF OBJECT_ID('tempdb..#IntersectionTable') IS NOT NULL DROP
				// Table #IntersectionTable \n"

				// + "CREATE MEMORY TEMPORARY TABLE CONVERSIONTABLE AS ( \n"
				// + "SELECT \n"
				// + "LineNumber \n"
				//
				// + ", tp.frequency \n"
				//
				// + ", 0 as TheLineOfIntersection \n"
				//
				// //+ " into #ConversionTable \n"
				//
				// + " FROM SettingsConversion conv \n"
				//
				// + "inner join Transponders tp \n"
				//
				// + "on conv.Transponder = tp.id \n"
				//
				// + " where parent_id = ? \n"
				//
				// + " ); \n"
				//
				// + "CREATE MEMORY TEMPORARY TABLE ManyFrequencies AS ( \n"
				//
				// + "select \n"
				// + "p1.LineNumber \n"
				// + ", p1.frequency \n"
				// + ", p1.TheLineOfIntersection \n"
				// //+ "into #ManyFrequencies \n"
				//
				// + "from #ConversionTable p1 \n"
				//
				// + "union \n"
				//
				// + "select \n"
				// + "p2.LineNumber \n"
				// + ", p2.frequency + 1 \n"
				// + ", p2.TheLineOfIntersection AS TheLineOfIntersection \n"
				// + "from #ConversionTable p2 \n"
				//
				// + "union \n"
				//
				// + "select \n"
				// + "p3.LineNumber \n"
				// + ", p3.frequency - 1 \n"
				// + ", p3.TheLineOfIntersection \n"
				// + "from #ConversionTable p3 \n"
				//
				// + " ); \n"
				//
				// + "CREATE MEMORY TEMPORARY TABLE IntersectionTable AS ( \n"
				//
				// + "select \n"
				// + "t1.LineNumber \n"
				// + ", t1.frequency \n"
				// + ", t2.LineNumber as TheLineOfIntersection \n"
				// //+ "into #IntersectionTable \n"
				// + "from #ManyFrequencies t1 \n"
				// + "inner join #ManyFrequencies t2 \n"
				// + "on t1.frequency = t2.frequency \n"
				// + "and t1.LineNumber <> t2.LineNumber \n"
				//
				// + " ); \n"

				+ "select distinct \n" + "conv.LineNumber \n"
				+ ", ISNULL(inter.TheLineOfIntersection, 0) AS TheLineOfIntersection \n"

				+ "from ConversionTable conv \n" + "inner join IntersectionTable inter \n"
				+ "on inter.LineNumber = conv.LineNumber \n"

				+ "order by \n" + "conv.LineNumber \n"

		// + "DROP TABLE #ConversionTable \n"
		// + "DROP TABLE #ManyFrequencies \n"
		// + "DROP TABLE #IntersectionTable \n"
				+ "";

	}

}
