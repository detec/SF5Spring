package org.openbox.sf5.jaxws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openbox.sf5.wsmodel.Satellites;
import org.openbox.sf5.wsmodel.WSException;

// https://github.com/javaee-samples/javaee7-samples/blob/master/jaxws/jaxws-client/src/test/java/org/javaee7/jaxws/client/EBookStoreClientSampleTest.java

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SatellitesServiceIT extends AbstractWSTest {

	public long satelliteId;

	public long getSatelliteId() {
		List<Satellites> satList = null;
		try {
			satList = SF5Port.getSatellitesByArbitraryFilter("Name", "13E");
		} catch (WSException e) {
			e.printStackTrace();
		}
		assertEquals(1, satList.size());

		Satellites readSat = satList.get(0);
		assertThat(readSat instanceof Satellites);

		satelliteId = readSat.getId();

		return satelliteId;
	}

    @BeforeEach
	public void setUp() throws Exception {
		setUpAbstractNoAuth();
	}

	@Test
	public void getAllSatellites() {
		List<Satellites> satList = null;
		try {
			satList = SF5Port.getAllSatellites();
		} catch (WSException e) {
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
		} catch (WSException e) {

			e.printStackTrace();
		}
		assertEquals("13E", returnSatellite.getName());

	}

}
