package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoptions;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.repository.AdoptionsRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAdoptionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionsService {
	
	private AdoptionsRepository adoptionsRepository;
	
	@Autowired
	public AdoptionsService(AdoptionsRepository adoptionsRepository) {
		this.adoptionsRepository = adoptionsRepository;
	}
	
	@Transactional(rollbackFor = DuplicatedAdoptionException.class)
	public void save (Adoptions adoptions) throws DataAccessException,DuplicatedAdoptionException {
		List<Adoptions> list = adoptionsRepository.findAll();
		if(adoptions.isNew() && list.stream()
				.anyMatch(a->a.getPet().equals(adoptions.getPet()) 
						&& a.getApplicant().equals(adoptions.getApplicant()) 
						&& a.getStatus().equals(Status.EN_PROCESO))) {
			throw new DuplicatedAdoptionException("No puede haber 2 adopciones a la misma mascota");
		} else {
			adoptionsRepository.save(adoptions);
		}
		
	}
	
	@Transactional(readOnly = true)
	public Adoptions findAdoptionById(int adoptionId) throws DataAccessException{
		return adoptionsRepository.findById(adoptionId).orElse(null);
	}
	
	@Transactional
	public void deleteAdoptionById(int adoptionId) throws DataAccessException{
		this.adoptionsRepository.deleteById(adoptionId);
	}
	
	@Transactional(readOnly = true)
	public List<Adoptions> findAll(){
		return this.adoptionsRepository.findAll();
	}
	
}
