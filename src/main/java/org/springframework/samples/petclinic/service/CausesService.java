package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausesService {

	private CauseRepository causeRepository;

	@Autowired
	public CausesService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}
	
	//buscar por id
	@Transactional(readOnly=true)
	public Cause findCause(Integer id) throws DataAccessException{
		return causeRepository.findById(id);
	}
	
	//buscar por name
	@Transactional(readOnly=true)
	public Collection<Cause> findCauseByName(String name) throws DataAccessException{
		return causeRepository.findByName(name);
	}
	
	//buscar todas las causas
	@Transactional(readOnly=true)
	public Collection<Cause> findAllCauses() throws DataAccessException{
		return causeRepository.findAll();
	}
	
	//guardar
	@Transactional
	public void saveCause(Cause cause) throws DataAccessException {
		cause.setCauseActive(true);
		causeRepository.save(cause);
	}
	
	//Devolver todas las donaciones
	@Transactional(readOnly = true)
	public Collection<Donation> getAllDonations(Integer id)  throws DataAccessException{
		return causeRepository.findById(id).getDonations();
	}

	//total alcanzado
	public Double currentBudget(Integer id) {
		return causeRepository.findById(id).getDonations().stream().mapToDouble(x->x.getAmount()).sum();
	}

	
	
	
}
