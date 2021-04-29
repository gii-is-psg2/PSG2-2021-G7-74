package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DonationTests extends ValidatorTests{
	
	private Donation createSUT(Double amount, String date, String client, Integer cause) {
		// If cause != 1 then the donation has no cause
		Cause c = null;
		if(cause == 1)
			c = new Cause(); //dummy cause
		
		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setDonationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		donation.setClient(client);
		donation.setCause(c);
		
		return donation;
	}
	
	@ParameterizedTest
	@CsvSource({
		"2000, 12/12/2020, Antonio Jose, 1",
		"1230, 12/12/2029, Manolo, 1",
		"1000, 28/01/2021, Tomas, 1",
	})
	void validateDonationNoErrorTest(Double amount, String date, String client, Integer cause) {
		Donation d = this.createSUT(amount, date, client, cause);
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(d);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		", 12/12/2020, Antonio Jose, 1",
		", 12/12/2029, Manolo, 1",
		", 28/01/2021, Tomas, 1",
	})
	void validateDonationAmountNotNullTest(Double amount, String date, String client, Integer cause) {
		Donation d = this.createSUT(amount, date, client, cause);
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(d);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1, 12/12/2020, Antonio Jose, 1",
		"0, 12/12/2029, Manolo, 1",
		"0.99, 28/01/2021, Tomas, 1",
	})
	void validateDonationAmountMinTest(Double amount, String date, String client, Integer cause) {
		Donation d = this.createSUT(amount, date, client, cause);
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(d);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"2000, 12/12/2020, , 1",
		"1230, 12/12/2029, '   ', 1",
		"1000, 28/01/2021, '', 1",
	})
	void validateDonationClientNotBlankTest(Double amount, String date, String client, Integer cause) {
		Donation d = this.createSUT(amount, date, client, cause);
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(d);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"2000, 12/12/2020, Antonio Jose, 0",
		"1230, 12/12/2029, Manolo, 0",
		"1000, 28/01/2021, Tomas, 0",
	})
	void validateDonationCauseNotNullTest(Double amount, String date, String client, Integer cause) {
		Donation d = this.createSUT(amount, date, client, cause);
		Validator validator = createValidator();
		Set<ConstraintViolation<Donation>> constraintViolations = validator.validate(d);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
