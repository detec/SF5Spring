package org.openbox.sf5.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openbox.sf5.db.Satellites;
import org.springframework.transaction.annotation.Transactional;

public class AbstractServiceTests {

	protected ObjectsController contr = new ObjectsController();

    @Test
    @Transactional
    public void shouldInsertSatellite() {

    	Satellites newSat = new Satellites();
    	newSat.setName("Test sat");
    	contr.saveOrUpdate(newSat);
    	assertThat(newSat.getId()).isNotEqualTo(0);
    }
}
