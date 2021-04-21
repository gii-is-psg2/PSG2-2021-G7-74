package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
@Table(name = "adoptions")


public class Adoptions extends BaseEntity{
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	
	@NotBlank
	private String description;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "applicant_id")
	private Owner applicant;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NotNull
	@PastOrPresent
	@Column(name = "application_date")
	private LocalDate date;

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Owner getApplicant() {
		return applicant;
	}

	public void setApplicant(Owner applicant) {
		this.applicant = applicant;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
