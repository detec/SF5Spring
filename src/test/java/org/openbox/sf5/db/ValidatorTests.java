package org.openbox.sf5.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
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
	public void shouldNotValidateWhenNameEmpty() {

		Settings setting = new Settings();
		setting.setName("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Settings>> constraintViolations = validator.validate(setting);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Settings> violation =  constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("Name");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");

        Transponders trans = new Transponders();
        Set<ConstraintViolation<Transponders>> trsnapondersConstraintViolations = validator.validate(trans);
        assertThat(constraintViolations.size()).isEqualTo(7);

        List<String> fields = new ArrayList<String>();
        fields.add("Frequency");
        fields.add("Polarization");
        fields.add("Carrier");
        fields.add("Speed");
        fields.add("VersionOfTheDVB");
        fields.add("RangeOfDVB");
        fields.add("Satellite");

        for (ConstraintViolation<Transponders> e : trsnapondersConstraintViolations) {
        	//int stringIndex = trsnapondersConstraintViolations.
        	assertThat(violation.getPropertyPath().toString()).isEqualTo("Name");
        	assertThat(e.getMessage()).isEqualTo("may not be empty");
        }
	}
}
