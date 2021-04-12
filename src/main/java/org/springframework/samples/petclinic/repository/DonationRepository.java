package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository extends Repository<Donation, Integer>{

	
	Donation findById(Integer id);
	void save(Donation donation);
	Collection<Donation> findAll();
	
}
