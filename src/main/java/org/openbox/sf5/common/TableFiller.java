package org.openbox.sf5.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openbox.sf5.dao.DVBRangeValuesRepository;
import org.openbox.sf5.dao.ValueOfTheCarrierFrequencyRepository;
import org.openbox.sf5.model.CarrierFrequency;
import org.openbox.sf5.model.KindsOfPolarization;
import org.openbox.sf5.model.RangesOfDVB;
import org.openbox.sf5.model.TheDVBRangeValues;
import org.openbox.sf5.model.ValueOfTheCarrierFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Fills necessary tables with settings.
 *
 * @author Andrii Duplyk
 *
 */
@Component
public class TableFiller {

    @Autowired
    private DVBRangeValuesRepository dvbRangeValuesRepository;

    @Autowired
    private ValueOfTheCarrierFrequencyRepository valueOfTheCarrierFrequencyRepository;

	/**
	 * This method executes when context loads.
	 *
	 * @param event
	 */
	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {

		List<RangesOfDVB> list = new ArrayList<>();
		list.add(RangesOfDVB.C);
		list.add(RangesOfDVB.KU);

		TheDVBRangeValues newRecord = null;
        List<TheDVBRangeValues> existingValues = this.dvbRangeValuesRepository.findAll();

        if (existingValues.isEmpty()) {
            for (RangesOfDVB e : list) {
                if (e.equals(RangesOfDVB.C)) {
                    newRecord = new TheDVBRangeValues(e, 3400, 4200);
                }

                if (e.equals(RangesOfDVB.KU)) {
                    newRecord = new TheDVBRangeValues(e, 10700, 12750);
                }
                this.dvbRangeValuesRepository.save(newRecord);
            }
        }

		ValueOfTheCarrierFrequency value;
		List<ValueOfTheCarrierFrequency> rec;

        Optional<ValueOfTheCarrierFrequency> valueOptional = this.valueOfTheCarrierFrequencyRepository
                .findByTypeOfCarrierFrequencyAndPolarization(
                CarrierFrequency.LOWER,
                KindsOfPolarization.PIE);

        if (!valueOptional.isPresent()) {
			value = new ValueOfTheCarrierFrequency(CarrierFrequency.LOWER, KindsOfPolarization.PIE, 10700, 11699);
            this.valueOfTheCarrierFrequencyRepository.save(value);
		}

        valueOptional = this.valueOfTheCarrierFrequencyRepository
                .findByTypeOfCarrierFrequencyAndPolarization(CarrierFrequency.LOWER,
                KindsOfPolarization.LINEAR);

        if (!valueOptional.isPresent()) {
			value = new ValueOfTheCarrierFrequency(CarrierFrequency.LOWER, KindsOfPolarization.LINEAR, 10700, 11699);
            this.valueOfTheCarrierFrequencyRepository.save(value);
		}

        valueOptional = this.valueOfTheCarrierFrequencyRepository
                .findByTypeOfCarrierFrequencyAndPolarization(CarrierFrequency.TOP,
                KindsOfPolarization.LINEAR);

        if (!valueOptional.isPresent()) {
			value = new ValueOfTheCarrierFrequency(CarrierFrequency.TOP, KindsOfPolarization.LINEAR, 11700, 12750);
            this.valueOfTheCarrierFrequencyRepository.save(value);
		}

        valueOptional = this.valueOfTheCarrierFrequencyRepository
                .findByTypeOfCarrierFrequencyAndPolarization(CarrierFrequency.C_RANGE,
                KindsOfPolarization.LINEAR);

        if (!valueOptional.isPresent()) {
			value = new ValueOfTheCarrierFrequency(CarrierFrequency.C_RANGE, KindsOfPolarization.LINEAR, 3400, 4200);
            this.valueOfTheCarrierFrequencyRepository.save(value);
		}

        valueOptional = this.valueOfTheCarrierFrequencyRepository
                .findByTypeOfCarrierFrequencyAndPolarization(CarrierFrequency.TOP_PIE, KindsOfPolarization.PIE);

        if (!valueOptional.isPresent()) {
			value = new ValueOfTheCarrierFrequency(CarrierFrequency.TOP_PIE, KindsOfPolarization.PIE, 11700, 12750);
            this.valueOfTheCarrierFrequencyRepository.save(value);
		}
	}

}
