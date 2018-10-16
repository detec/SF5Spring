package org.openbox.sf5.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openbox.sf5.config.AppTestConfiguration;
import org.openbox.sf5.json.service.AbstractJsonizerTest;
import org.openbox.sf5.model.TheDVBRangeValues;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = { AppTestConfiguration.class })
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Component
public class TableFillerTests extends AbstractJsonizerTest {

    @BeforeEach
	public void setUp() {
		super.setUpAbstract();
	}

	@Test
	@Transactional
	public void shouldFillTablesByTableFiller() {

		// there should be 2 records in THEDVBRANGEVALUES
		List<TheDVBRangeValues> rangesList = objectController.list(TheDVBRangeValues.class);
		assertThat(rangesList.size()).isEqualTo(2);
	}
}
