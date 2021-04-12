package org.springframework.samples.petclinic.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Constraint(validatedBy = HotelBookValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface HotelBookConstraint {
	String message() default "Ya existe una reserva de hotel para esa mascota en esas fechas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
