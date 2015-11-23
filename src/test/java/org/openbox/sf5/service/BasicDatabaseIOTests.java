package org.openbox.sf5.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.openbox.sf5.dao.DAO;
import org.openbox.sf5.dao.DAOImpl;
import org.openbox.sf5.model.AbstractDbEntity;
import org.openbox.sf5.model.CarrierFrequency;
import org.openbox.sf5.model.DVBStandards;
import org.openbox.sf5.model.Polarization;
import org.openbox.sf5.model.RangesOfDVB;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.TypesOfFEC;
import org.openbox.sf5.model.Users;
import org.reflections.Reflections;
import org.springframework.transaction.annotation.Transactional;

public class BasicDatabaseIOTests {

	private DAO DAO;

	private ObjectService service;

	private ObjectsController contr;

	@Before
	public void setUp() {

		Configuration configuration = new Configuration().configure();

		Set<Class<? extends AbstractDbEntity>> annotatedSet = getAllSubclassesAbstractDbEntity();

		// adding classes as annotated.
		annotatedSet.stream().forEach(t -> configuration.addAnnotatedClass(t));

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

		sessionFactory.openSession();

		DAO = new DAOImpl();
		DAO.setSessionFactory(sessionFactory);

		service = new ObjectServiceImpl();
		service.setDAO(DAO);

		contr = new ObjectsController();
		contr.setService(service);

	}

	public static Set<Class<? extends AbstractDbEntity>> getAllSubclassesAbstractDbEntity() {
		Reflections reflections = new Reflections("org.openbox.sf5");

		Set<Class<? extends AbstractDbEntity>> subTypes = reflections.getSubTypesOf(AbstractDbEntity.class);
		return subTypes;

	}

	@Test
	@Transactional
	public void shouldInsertSatellite() {

		Satellites newSat = getNewSatellite();
		contr.saveOrUpdate(newSat);
		assertThat(newSat.getId()).isNotEqualTo(0);
	}

	@Test
	@Transactional
	public void shouldInsertTransponder() {
		Transponders trans = getNewTransponder();
		contr.saveOrUpdate(trans);
		assertThat(trans.getId()).isNotEqualTo(0);

	}

	@Test
	@Transactional
	public void shouldInsertSetting() {
		Settings setting = getNewSetting();
		contr.saveOrUpdate(setting);
		assertThat(setting.getId()).isNotEqualTo(0);
	}

	private Satellites getNewSatellite() {
		Satellites newSat = new Satellites();
		newSat.setName("Test sat");

		return newSat;
	}

	private Transponders getNewTransponder() {
		Satellites newSat = getNewSatellite();
		contr.saveOrUpdate(newSat);

		Transponders trans = new Transponders();
		trans.setCarrier(CarrierFrequency.Top);
		trans.setFEC(TypesOfFEC._23);
		trans.setFrequency(11555);
		trans.setPolarization(Polarization.V);
		trans.setRangeOfDVB(RangesOfDVB.Ku);
		trans.setSatellite(newSat);
		trans.setSpeed(10000);
		trans.setVersionOfTheDVB(DVBStandards.DVBS2);

		return trans;
	}

	private SettingsConversion getNewSettingsConversionLine() {
		Transponders trans = getNewTransponder();
		trans.setSpeed(10500);
		contr.saveOrUpdate(trans);

		SettingsConversion sc = new SettingsConversion();
		sc.setLineNumber(1);
		sc.setNote("First");
		sc.setSatindex(1);
		sc.setTpindex(2);
		sc.setTransponder(trans);

		return sc;
	}

	private Users getNewUser() {
		Users user = new Users();
		user.setenabled(true);
		user.setPassword("1");
		user.setusername("testuser");
		return user;
	}

	private Settings getNewSetting() {
		Users user = getNewUser();
		contr.saveOrUpdate(user);

		Settings setting = new Settings();
		setting.setName("Test");
		setting.setUser(user);

		List<SettingsConversion> scList = new ArrayList<SettingsConversion>();

		SettingsConversion sc1 = getNewSettingsConversionLine();
		sc1.setLineNumber(1);
		sc1.setparent_id(setting);

		SettingsConversion sc2 = getNewSettingsConversionLine();
		sc2.setLineNumber(2);
		sc2.setparent_id(setting);
		sc2.setNote("Useful");

		scList.add(sc1);
		scList.add(sc2);
		setting.setConversion(scList);

		return setting;
	}
}
