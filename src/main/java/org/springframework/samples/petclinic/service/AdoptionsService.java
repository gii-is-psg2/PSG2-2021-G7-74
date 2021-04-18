package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoptions;
import org.springframework.samples.petclinic.repository.AdoptionsRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAdoptionException;
import org.springframework.stereotype.Service;

@Service
public class AdoptionsService {
	
	@Autowired
	private AdoptionsRepository adoptionsRepository;
	
	public void save (Adoptions adoptions) throws DataAccessException,DuplicatedAdoptionException {
		
		if(adoptionsRepository.findAll().contains(adoptions)) {
			throw new DuplicatedAdoptionException("No puede haber 2 adopciones a la misma mascota");
		} else {
			adoptionsRepository.save(adoptions);
		}
		
	}
	
	

}
