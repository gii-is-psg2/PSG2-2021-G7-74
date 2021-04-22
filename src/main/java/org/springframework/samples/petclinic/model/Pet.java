/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Visit> visits;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<HotelBook> hotelBooks;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Adoptions> adoptions;
	
	@NotNull
	@JoinColumn(name = "adoptable")
	private Boolean adoptable;
	
	
	public Boolean getAdoptable() {
		return this.adoptable;
	}
	
	public void setAdoptable(Boolean adoptable) {
		this.adoptable = adoptable;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public PetType getType() {
		return this.type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public Owner getOwner() {
		return this.owner;
	}

	protected void setOwner(Owner owner) {
		this.owner = owner;
	}

	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}
	
	protected Set<HotelBook> getHotelBooksInternal() {
		if (this.hotelBooks == null) {
			this.hotelBooks = new HashSet<>();
		}
		return this.hotelBooks;
	}
	
	protected Set<Adoptions> getAdoptionsInternal(){
		if (this.adoptions == null) {
			this.adoptions = new HashSet<>();
		}
		return this.adoptions;
	}

	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}
	
	protected void setHotelBooks(Set<HotelBook> hotelBooks) {
		this.hotelBooks = hotelBooks;
	}
	
	protected void setAdoptionsInternal(Set<Adoptions> adoptions) {
		this.adoptions = adoptions;
	}

	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}
	
	public List<HotelBook> getHotelBooks() {
		List<HotelBook> sortedHotelBooks = new ArrayList<>(getHotelBooksInternal());
		PropertyComparator.sort(sortedHotelBooks, new MutableSortDefinition("startDate",false,false));
		return Collections.unmodifiableList(sortedHotelBooks);
	}
	
	public List<Adoptions> getAdoptions(){
		List<Adoptions> sortedAdoptions = new ArrayList<>(getAdoptionsInternal());
		PropertyComparator.sort(sortedAdoptions, new MutableSortDefinition("date",false,false));
		return Collections.unmodifiableList(sortedAdoptions);
	}

	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);
		visit.setPet(this);
	}
	
	public void addHotelBook(HotelBook hotelBook) {
		getHotelBooksInternal().add(hotelBook);
		hotelBook.setPet(this);
	}
	
	public void addAdoption(Adoptions adoption) {
		getAdoptionsInternal().add(adoption);
		adoption.setPet(this);
	}
	
	public void removeAdoption(Adoptions adoption) {
		getAdoptionsInternal().remove(adoption);
	}
}
