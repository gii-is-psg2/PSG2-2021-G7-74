package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;


@Entity
@Table(name = "hotel_books")
public class HotelBook extends BaseEntity{
	
	@NotNull
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;
	
	@NotNull
	@Column(name = "end_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;
	
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
