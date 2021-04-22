package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Adoption;

public interface AdoptionsRepository extends Repository<Adoption,Integer>{
	
	
	void save(Adoption adoptions) throws DataAccessException;
	
	//List<Adoptions> findByOwner(Integer ownerId);
	
	void deleteById(int id);
	
	List<Adoption> findAll();
	
	Optional<Adoption> findById(int id);
	
	

}
