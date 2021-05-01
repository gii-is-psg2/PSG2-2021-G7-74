package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdoptionsTests extends ValidatorTests {

	private Adoption createSUT(Integer pet, String description, Integer owner, String status, String date) {

		Pet p = null;
		Owner own = null;

		if (pet != null && pet > 0) {
			p = new Pet();
		}

		if (owner != null && owner > 0) {
			own = new Owner();
		}

		Adoption adop = new Adoption();
		adop.setPet(p);
		adop.setDescription(description);
		adop.setApplicant(own);
		adop.setStatus(status == null ? null : Status.valueOf(status));
		adop.setDate(date == null ? null : LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		return adop;
	}

	@ParameterizedTest
	@CsvSource({ "1,I am going to treat Lucky very good, 1,ACEPTADA,21/02/2021",
			"1,I am going to treat Joan very good, 1,EN_PROCESO,21/03/2021",
			"1,I love your pet!, 1,DENEGADA,21/09/2020", })
	void validateAdoptionNoErrorTest(Integer pet, String description, Integer owner, String status, String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isZero();
	}

	@ParameterizedTest
	@CsvSource({ ",I am going to treat Lucky very good, 1,ACEPTADA,21/02/2021",
			"0,I am going to treat Joan very good, 1,EN_PROCESO,21/03/2021",
			"-1,I love your pet!, 1,DENEGADA,21/09/2020", })
	void validateAdoptionPetIsNull(Integer pet, String description, Integer owner, String status, String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({ "1, , 1,ACEPTADA,21/02/2021",
		"1,, 1,EN_PROCESO,21/03/2021", })
	void validateAdoptionDescriptionIsBlank(Integer pet, String description, Integer owner, String status,
			String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({ "1,I am going to treat Lucky very good,0,ACEPTADA,21/02/2021",
			"1,I am going to treat Joan very good,,EN_PROCESO,21/03/2021",
			"1,I love your pet!,-8,DENEGADA,21/09/2020", })
	void validateAdoptionApplicantIsNull(Integer pet, String description, Integer owner, String status, String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({ "1,I am going to treat Lucky very good,1,,21/02/2021",
			"1,I am going to treat Joan very good,1,,21/03/2021", })
	void validateAdoptionStatusIsNull(Integer pet, String description, Integer owner, String status, String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({ "1,I am going to treat Lucky very good,1,ACEPTADA,",
			"1,I am going to treat Joan very good,1,EN_PROCESO,", })
	void validateAdoptiondDateIsNull(Integer pet, String description, Integer owner, String status, String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({ "1,I am going to treat Lucky very good,1,ACEPTADA,21/08/2021",
			"1,I am going to treat Joan very good,1,EN_PROCESO,31/12/2021", })
	void validateAdoptiondDateIsFuture(Integer pet, String description, Integer owner, String status, String date) {
		Adoption adop = this.createSUT(pet, description, owner, status, date);
		Validator validator = createValidator();
		Set<ConstraintViolation<Adoption>> constraintViolations = validator.validate(adop);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
}
