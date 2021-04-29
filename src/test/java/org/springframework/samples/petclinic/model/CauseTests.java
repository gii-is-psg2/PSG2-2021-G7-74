package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CauseTests extends ValidatorTests{
	
	private Cause createSUT(String name, String description, Double budgetTarget, String organization, Boolean active) {
		// this test does not test donation so we use a dummy donation
		Donation dummyDonation = new Donation();
		
		Cause cause = new Cause();
		cause.setName(name);
		cause.setDescription(description);
		cause.setBudgetTarget(budgetTarget);
		cause.setOrganization(organization);
		cause.setCauseActive(active);
		cause.addDonation(dummyDonation);
		
		return cause;
	}
	
	@ParameterizedTest
	@CsvSource({
		"Green Peace, Causa para evitar el abandono de animales, 10000, Green Peace, true",
		"WWF for the animals, Causa para evitar las especies invasivas, 13000, WWF, true",
		"Save the pandas, Causa para evitar la extincion del panda, 1000, WWF, false",
	})
	void validateCauseNoErrorTest(String name, String description, Double budgetTarget, String organization, Boolean active) {
		Cause c = this.createSUT(name, description, budgetTarget, organization, active);
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(c);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		" , Causa para evitar el abandono de animales, 10000, Green Peace, true",
		" '    ', Causa para evitar las especies invasivas, 13000, WWF, true",
		" '', Causa para evitar la extincion del panda, 1000, WWF, false",
	})
	void validateCauseNameNotBlankTest(String name, String description, Double budgetTarget, String organization, Boolean active) {
		Cause c = this.createSUT(name, description, budgetTarget, organization, active);
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(c);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		" Green Peace, '', 10000, Green Peace, true",
		" WWF for the animals, '       ', 13000, WWF, true",
		" Save the pandas,  , 1000, WWF, false",
	})
	void validateDescriptionNameNotBlankTest(String name, String description, Double budgetTarget, String organization, Boolean active) {
		Cause c = this.createSUT(name, description, budgetTarget, organization, active);
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(c);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Green Peace, Causa para evitar el abandono de animales, -1, Green Peace, true",
		"WWF for the animals, Causa para evitar las especies invasivas, 0, WWF, true",
		"Save the pandas, Causa para evitar la extincion del panda, -1, WWF, false",
	})
	void validateDescriptionBudgetMinTest(String name, String description, Double budgetTarget, String organization, Boolean active) {
		Cause c = this.createSUT(name, description, budgetTarget, organization, active);
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(c);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Green Peace, Causa para evitar el abandono de animales, 10000, '', true",
		"WWF for the animals, Causa para evitar las especies invasivas, 13000, , true",
		"Save the pandas, Causa para evitar la extincion del panda, 1000, '       ', false",
	})
	void validateCauseOrganizationNotBlankTest(String name, String description, Double budgetTarget, String organization, Boolean active) {
		Cause c = this.createSUT(name, description, budgetTarget, organization, active);
		Validator validator = createValidator();
		Set<ConstraintViolation<Cause>> constraintViolations = validator.validate(c);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
}
