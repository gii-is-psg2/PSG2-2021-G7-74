package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;


@Entity
@Table(name = "hotel_books")
public class HotelBook extends BaseEntity{
	
	@NotNull
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@PastOrPresent(message = "La fecha debe ser pasada o presente")
	private LocalDate startDate;
	
	@NotNull
	@Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@FutureOrPresent(message = "La fecha debe ser presente o futura")
	private LocalDate endDate;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	public HotelBook() {
		super();
	}

	public Pet getPet() {
		return this.pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return this.endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


}
