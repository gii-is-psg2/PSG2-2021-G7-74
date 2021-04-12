package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
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
	
	
	//buscar todos
	@Transactional(readOnly=true)
	public Collection<Cause> findAllCauses() throws DataAccessException{
		return causeRepository.findAll();
	}
	
	//guardar
	@Transactional
	public void saveCause(Cause cause) {
		causeRepository.save(cause);
	}
	
	
	
	
	//borrar
	public void deleteCause(Integer id) {
		causeRepository.deleteById(id);
	}
	
	
	
	//total alcanzado
	public Double currentBudget(Integer id) {
		return causeRepository.findById(id).getDonations().stream().mapToDouble(x->x.getAmount()).sum();
	}
	
	
	//cuanto falta
	public Double remainingBudgetTarget(Integer id) {
		Double target=causeRepository.findById(id).getBudgetTarget();
		
		return target - currentBudget(id);
	}
	
	
	
}
