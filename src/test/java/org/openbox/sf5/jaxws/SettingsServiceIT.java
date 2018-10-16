package org.openbox.sf5.jaxws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openbox.sf5.json.common.BuildTestSetting;
import org.openbox.sf5.wsmodel.Settings;
import org.openbox.sf5.wsmodel.Transponders;
import org.openbox.sf5.wsmodel.Users;
import org.openbox.sf5.wsmodel.WSException;
import org.openbox.sf5.wsmodel.WSException_Exception;

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SettingsServiceIT extends AbstractWSTest {

	@Test
	public void shouldCreateAndGetSettingById() throws WSException_Exception {
		Users adminUser = getTestUser();

		assertThat(adminUser).isNotNull();

		List<Transponders> newTransList = null;

		try {
            newTransList = SF5Port.getTranspondersByArbitraryFilter("speed", "27500");
		} catch (WSException e1) {

			e1.printStackTrace();
		}

		assertThat(newTransList.size()).isGreaterThan(0);

		Settings setting = BuildTestSetting.buildSetting(adminUser, newTransList, "Simple");

		long newSettID = 0L;
		try {
			newSettID = SF5Port.createSetting(setting);

		} catch (WSException e) {
			e.printStackTrace();
		}

		assertThat(newSettID).isNotNull();
		setting.setId(newSettID);

		Settings settingRead = null;
		try {
			settingRead = SF5Port.getSettingById(newSettID);
		} catch (WSException e) {
			System.out.println("Error getting setting by ID " + newSettID + e);
		}
		assertThat(settingRead).isNotNull();
		assertTrue(settingRead instanceof Settings);

	}

	private Users getTestUser() {
		try {
            return SF5Port.getUserByLogin(testUsername);
		} catch (WSException e) {
			e.printStackTrace();
            return null;
		}
	}

    @BeforeEach
	public void setUp() throws Exception {
		setUpAbstract();
	}

}
