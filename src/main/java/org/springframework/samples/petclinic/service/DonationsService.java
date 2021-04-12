package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DonationsService {

	private DonationRepository donationRepository;

	@Autowired
	public DonationsService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}
	
	//buscar por id
	@Transactional(readOnly = true)
	public Donation findDonation(Integer id) {
		return donationRepository.findById(id);
		
	}
	
	
	
	//buscar todas
	@Transactional(readOnly = true)
	public Collection<Donation> findAllDonations(){
		return donationRepository.findAll();
	}
	
	
	//guardar 
	@Transactional
	public void saveDonation(Donation donation) {
		donationRepository.save(donation);
	}
	
	
}
