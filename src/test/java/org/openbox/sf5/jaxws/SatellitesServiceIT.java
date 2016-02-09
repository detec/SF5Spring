package org.openbox.sf5.jaxws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.wsmodel.Satellites;
import org.openbox.sf5.wsmodel.WSException_Exception;

// https://github.com/javaee-samples/javaee7-samples/blob/master/jaxws/jaxws-client/src/test/java/org/javaee7/jaxws/client/EBookStoreClientSampleTest.java

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// @ContextConfiguration(locations = {
// "file:src/main/resources/spring/autowired-beans.xml" })
// @WebAppConfiguration
public class SatellitesServiceIT extends AbstractWSTest {

	public long satelliteId;

	public long getSatelliteId() {
		List<Satellites> satList = null;
		try {
			satList = SF5Port.getSatellitesByArbitraryFilter("Name", "13E");
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		assertEquals(1, satList.size());

		Satellites readSat = satList.get(0);
		assertThat(readSat instanceof Satellites);

		satelliteId = readSat.getId();

		return satelliteId;
	}

	@Before
	public void setUp() throws Exception {
		setUpAbstractNoAuth();
	}

	// @Test
	// public void getSatWithID1() {
	//
	// Satellites returnSatellite = null;
	// try {
	// returnSatellite = SF5Port.getSatelliteById(1);
	// } catch (WSException_Exception e) {
	//
	// e.printStackTrace();
	// }
	// assertEquals("4.8E", returnSatellite.getName());
	// }

	@Test
	public void getAllSatellites() {
		List<Satellites> satList = null;
		try {
			satList = SF5Port.getAllSatellites();
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		assertEquals(3, satList.size());

	}

	@Test
	public void getSatellitesByArbitraryFilter() {

		Satellites returnSatellite = null;

		getSatelliteId();

		try {
			returnSatellite = SF5Port.getSatelliteById(satelliteId);
		} catch (WSException_Exception e) {

			e.printStackTrace();
		}
		assertEquals("13E", returnSatellite.getName());

	}

}
