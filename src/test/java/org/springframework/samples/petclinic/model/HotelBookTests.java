package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HotelBookTests extends ValidatorTests {
	
	private HotelBook createSUT(String startDate, String endDate,Integer pet) {
		Pet p = null;
		// if pet == 1 ==> pet exists
		// else pet does not exist
		if(pet!=null && pet>0) {
			p = new Pet();
		}
		HotelBook hb = new HotelBook();
		hb.setStartDate(startDate == null ? null : LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		hb.setEndDate(endDate == null ? null : LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		hb.setPet(p);

		return hb;
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2020, 21/05/2021, 1",
		"21/02/2021, 13/07/2021, 1",
		"21/01/2021, 18/06/2021, 1",
	})
	void validateHotelBookNoErrorTest(String startDate, String endDate,Integer pet) {
		HotelBook hb = this.createSUT(startDate, endDate, pet);
		Validator validator = createValidator();
		Set<ConstraintViolation<HotelBook>> constraintViolations = validator.validate(hb);
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2020, 21/11/2020, 1",
	})
	void validateHotelBookStartDateIsPastOrPresentTest(String startDate, String endDate,Integer pet) {
		HotelBook hb = this.createSUT(startDate, endDate, pet);
		Validator validator = createValidator();
		Set<ConstraintViolation<HotelBook>> constraintViolations = validator.validate(hb);
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	@ParameterizedTest
	@CsvSource({
		",21/10/2021,1",
		",13/08/2021, 1",
	})
	void validateHotelBookStartDateIsNullTest(String startDate, String endDate,Integer pet) {
		HotelBook hb = this.createSUT(startDate, endDate, pet);
		Validator validator = createValidator();
		Set<ConstraintViolation<HotelBook>> constraintViolations = validator.validate(hb);
		assertThat(constraintViolations.size()).isEqualTo(2);	
	}

	@ParameterizedTest
	@CsvSource({
		"21/10/2020, 21/11/2020, 1",
		"21/10/2021, 13/11/2021, 1",
		"21/02/2021, 18/03/2021, 1",
	})
	void validateHotelBookEndDateIsPresentOrFutureTest(String startDate, String endDate,Integer pet) {
		HotelBook hb = this.createSUT(startDate, endDate, pet);
		Validator validator = createValidator();
		Set<ConstraintViolation<HotelBook>> constraintViolations = validator.validate(hb);
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2020,,1",
		"13/02/2021,,1",
	})
	void validateHotelBookEndDateIsNullTest(String startDate, String endDate,Integer pet) {
		HotelBook hb = this.createSUT(startDate, endDate, pet);
		Validator validator = createValidator();
		Set<ConstraintViolation<HotelBook>> constraintViolations = validator.validate(hb);
		assertThat(constraintViolations.size()).isEqualTo(2);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"21/10/2020, 21/05/2021, ",
		"21/10/2020, 21/05/2021, 0",
		"21/10/2020, 21/05/2021, -1",
	})
	void validateHotelBookPetNullTest(String startDate, String endDate,Integer pet) {
		HotelBook hb = this.createSUT(startDate, endDate, pet);
		Validator validator = createValidator();
		Set<ConstraintViolation<HotelBook>> constraintViolations = validator.validate(hb);
		assertThat(constraintViolations.size()).isEqualTo(2);
	}
	

}
