package org.openbox.sf5.jaxws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.json.common.BuildTestSetting;
import org.openbox.sf5.wsmodel.Settings;
import org.openbox.sf5.wsmodel.Transponders;
import org.openbox.sf5.wsmodel.Users;
import org.openbox.sf5.wsmodel.WSException_Exception;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = { "file:src/main/resources/spring/autowired-beans.xml" })
@WebAppConfiguration
public class SettingsServiceIT extends AbstractWSTest {

	@Test
	public void shouldCreateAndGetSettingById() {
		Users adminUser = getTestUser();

		assertThat(adminUser).isNotNull();

		List<Transponders> newTransList = null;
		try {
			newTransList = SF5Port.getTranspondersByArbitraryFilter("Speed", "27500");
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		assertThat(newTransList.size()).isGreaterThan(0);

		Settings setting = BuildTestSetting.buildSetting(adminUser, newTransList, "Simple");

		long newSettID = 0;
		try {
			newSettID = SF5Port.createSetting(setting);
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		assertThat(newSettID).isGreaterThan(0);
		setting.setId(newSettID);

		Settings settingRead = null;
		try {
			settingRead = SF5Port.getSettingById(newSettID);
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		assertThat(settingRead).isNotNull();
		assertTrue(settingRead instanceof Settings);

	}

	private Users getTestUser() {

		Users testUser = null;
		try {
			testUser = SF5Port.getUserByLogin(testUsername);
		} catch (WSException_Exception e) {

			e.printStackTrace();
		}

		return testUser;
	}

	@Before
	public void setUp() throws Exception {
		setUpAbstract();
	}

}
