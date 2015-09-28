package org.openbox.sf5.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	public void shouldNotValidateWhenFieldsEmpty() {

		Settings setting = new Settings();
		setting.setName("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Settings>> constraintViolations = validator.validate(setting);
		assertThat(constraintViolations.size()).isEqualTo(1);

		ConstraintViolation<Settings> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("Name");
		assertThat(violation.getMessage()).isEqualTo("may not be empty");

		Transponders trans = new Transponders();

		// first round. All fields are empty
		Set<ConstraintViolation<Transponders>> trsnapondersConstraintViolations = validator.validate(trans);
		assertThat(trsnapondersConstraintViolations.size()).isEqualTo(7);
		// we move iterator in the cycle
		// ConstraintViolation<Transponders> transViolation =
		// trsnapondersConstraintViolations.iterator().next();

		Iterator<ConstraintViolation<Transponders>> iterator = trsnapondersConstraintViolations.iterator();

		// Preparing to check fields.
		// List<String> fields = new ArrayList<String>();
		// fields.add("Frequency");
		// fields.add("Polarization");
		// fields.add("Carrier");
		// fields.add("Speed");
		// fields.add("VersionOfTheDVB");
		// fields.add("RangeOfDVB");
		// fields.add("Satellite");

		// HashMap<String, Object> valuesmap = new HashMap<String, Object>();
		// valuesmap.put("Frequency", (long) 11000);
		// valuesmap.put("Polarization", Polarization.H);
		// valuesmap.put("Carrier", CarrierFrequency.Top);
		// valuesmap.put("Speed", (long) 27500);
		// valuesmap.put("VersionOfTheDVB", DVBStandards.DVBS);
		// valuesmap.put("RangeOfDVB", RangesOfDVB.Ku);
		// valuesmap.put("Satellite", new Satellites());

		// RangeOfDVB may not be null
		// Carrier may not be null
		// Satellite may not be null
		// Polarization may not be null
		// VersionOfTheDVB may not be null
		// Speed must be greater than or equal to 1000
		// Frequency must be greater than or equal to 2000

		HashMap<String, String> valuesmap = new HashMap<String, String>();
		valuesmap.put("RangeOfDVB", "may not be null");
		valuesmap.put("Carrier", "may not be null");
		valuesmap.put("Satellite", "may not be null");
		valuesmap.put("Polarization", "may not be null");
		valuesmap.put("VersionOfTheDVB", "may not be null");
		valuesmap.put("Speed", "must be greater than or equal to 1000");
		valuesmap.put("Frequency", "must be greater than or equal to 2000");

		// Iterator it = mp.entrySet().iterator();

		// Iterator<Entry<String, Object>> iterator =
		// valuesmap.entrySet().iterator();

		// Class<?> clazz = trans.getClass();
		// Field chap = c.getDeclaredField("chapters");

		while (iterator.hasNext()) {
			// assign value to transponder being tested.
			// String fieldName = iterator.next().getKey();

			// try {
			// getting field and setting its value
			// Field fieldObj = clazz.getDeclaredField(fieldName);
			// cannot access private field
			// fieldObj.set(trans, iterator.next().getValue());

			// production code should handle these exceptions more
			// gracefully
			// } catch (NoSuchFieldException x) {
			// x.printStackTrace();
			// } catch (IllegalAccessException x) {
			// x.printStackTrace();
			// }

			// trsnapondersConstraintViolations = validator.validate(trans);
			// at the last round no violations should be fired.
			// if (iterator.hasNext()) {
			// assertThat(trsnapondersConstraintViolations.size()).isEqualTo(1);
			// ConstraintViolation<Transponders> transViolation =
			// trsnapondersConstraintViolations.iterator().next();

			ConstraintViolation<Transponders> transViolation = iterator.next();

			// There is no obvious need to check fields' names/
			// assertThat(transViolation.getPropertyPath().toString()).isEqualTo(fieldName);
			System.out.println(transViolation.getPropertyPath().toString() + " " + transViolation.getMessage());
			// assertThat(transViolation.getMessage()).isEqualTo("may not be
			// empty");
			// }
			//
			// else {
			// assertThat(trsnapondersConstraintViolations.size()).isEqualTo(0);
			// }

		}

		// Iterator<Integer> iterator = set.iterator();
		// while(iterator.hasNext()) {
		// Integer setElement = iterator.next();
		// if(setElement==2) {
		// iterator.remove();
		// }
		// }

		// List<ConstraintViolation<Transponders>> indexList = new
		// ArrayList<ConstraintViolation<Transponders>>();
		//
		// trsnapondersConstraintViolations.stream().forEach(t ->
		// indexList.add(t));
		//
		// Set<ConstraintViolation<Transponders>>
		// trsnapondersConstraintViolations = validator.validate(trans);
		// assertThat(constraintViolations.size()).isEqualTo(1);
		//
		// for (ConstraintViolation<Transponders> e :
		// trsnapondersConstraintViolations) {
		// // we need to take the name of the field to find it in the assertion
		// int stringIndex = indexList.indexOf(e);
		// String fieldName = fields.get(stringIndex);
		//
		// assertThat(violation.getPropertyPath().toString()).isEqualTo(fieldName);
		// assertThat(e.getMessage()).isEqualTo("may not be empty");
		// }

	}
}
