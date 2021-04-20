package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="donations")

public class Donation extends BaseEntity{
	
	
	@Column(name = "amount")
	@Min(0)
	@NotNull
	private Double amount;
	
	@Column(name = "donation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate donationDate;
	
	@Column(name="client")
	@NotEmpty
	private String client;
	
	@NotNull
	@ManyToOne(optional=false)
	private Cause cause;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDonation_date() {
		return donationDate;
	}

	public void setDonation_date(LocalDate donation_date) {
		this.donationDate = donation_date;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	public Donation() {
		this.donationDate = LocalDate.now();
		this.cause = new Cause();
	}





}
