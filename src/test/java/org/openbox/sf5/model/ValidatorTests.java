package org.openbox.sf5.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTests {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldNotValidateWhenFieldsEmpty() {

		HashMap<String, String> valuesmap = new HashMap<String, String>();
		valuesmap.put("User", "may not be null");
		valuesmap.put("Name", "may not be empty");
		valuesmap.put("TheLastEntry", "may not be null");

		Settings setting = new Settings();
		setting.setName("");

		Set<ConstraintViolation<Settings>> constraintViolations = validator.validate(setting);
		assertThat(constraintViolations.size()).isEqualTo(3);

		Iterator<ConstraintViolation<Settings>> settingIterator = constraintViolations.iterator();

		while (settingIterator.hasNext()) {
			ConstraintViolation<Settings> settingViolation = settingIterator.next();
			String propertyPath = settingViolation.getPropertyPath().toString();
			assertThat(valuesmap.get(propertyPath).equals(settingViolation.getMessage()));
		}

		Transponders trans = new Transponders();

		// first round. All fields are empty
		Set<ConstraintViolation<Transponders>> trsnapondersConstraintViolations = validator.validate(trans);
		assertThat(trsnapondersConstraintViolations.size()).isEqualTo(7);
		// we move iterator in the cycle

		Iterator<ConstraintViolation<Transponders>> iterator = trsnapondersConstraintViolations.iterator();

		valuesmap.put("RangeOfDVB", "may not be null");
		valuesmap.put("Carrier", "may not be null");
		valuesmap.put("Satellite", "may not be null");
		valuesmap.put("Polarization", "may not be null");
		valuesmap.put("VersionOfTheDVB", "may not be null");
		valuesmap.put("Speed", "must be greater than or equal to 1000");
		valuesmap.put("Frequency", "must be greater than or equal to 2000");

		while (iterator.hasNext()) {
			ConstraintViolation<Transponders> transViolation = iterator.next();
			String propertyPath = transViolation.getPropertyPath().toString();
			assertThat(valuesmap.get(propertyPath).equals(transViolation.getMessage()));
		}
	}
}
