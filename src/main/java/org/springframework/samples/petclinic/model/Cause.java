package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="causes")

public class Cause extends BaseEntity {
	
	@Column(name = "name")
	@NotBlank
	private String name;
	
	@Column(name = "description")
	@NotBlank
	private String description;
	
	@Column(name = "budgetTarget")
	@Min(1)
	private Double budgetTarget;
	
	@Column(name = "organization")
	@NotBlank
	private String organization;
	
	@Column(name="cause_active")
	private Boolean causeActive;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause")
	private Set<Donation> donations;
		
	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}

	public Boolean getCauseActive() {
		return causeActive;
	}


	public void setCauseActive(Boolean causeActive) {
		this.causeActive = causeActive;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getBudgetTarget() {
		return budgetTarget;
	}


	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	protected Set<Donation> getDonationsInternal(){
		if (this.donations == null) {
			this.donations = new HashSet<Donation>();
		}
		return this.donations;
	}
	
	protected void setDonationsInternal(Set<Donation> donations) {
		this.donations = donations;
	}
	
	public void addDonation(Donation donation) {
		if(this.getCauseActive()==true) {
		getDonationsInternal().add(donation);
		donation.setCause(this);
		}
	}

	public Double getAchievedTarget() {
		return getDonationsInternal().stream().mapToDouble(x->x.getAmount()).sum();
	}



}
