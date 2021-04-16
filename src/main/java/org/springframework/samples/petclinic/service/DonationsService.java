package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DonationsService {

	private DonationRepository donationRepository;

	@Autowired
	private CausesService causeService;
	
	@Autowired
	public DonationsService(DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}
	
	//buscar por id
	@Transactional(readOnly = true)
	public Donation findDonation(Integer id)  throws DataAccessException {
		return donationRepository.findById(id);
		
	}
	
	//buscar todas
	@Transactional(readOnly = true)
	public Collection<Donation> findAllDonations()  throws DataAccessException{
		return donationRepository.findAll();
	}
	
	
	//guardar 
	@Transactional
	public void saveDonation(Donation donation) throws DataAccessException {
		
		Cause cause = donation.getCause();
			if(causeService.currentBudget(cause.getId()) + donation.getAmount() < cause.getBudgetTarget()) {
				donation.setDonation_date(LocalDate.now());
				donationRepository.save(donation);
			}
			else {
				cause.setCause_active(false);
				donation.setDonation_date(LocalDate.now());
				donationRepository.save(donation);	
		}
				
	}
	
	
}
