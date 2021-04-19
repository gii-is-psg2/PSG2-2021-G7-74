package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Adoptions;

public interface AdoptionsRepository extends Repository<Adoptions,Integer>{
	
	
	void save(Adoptions adoptions) throws DataAccessException;
	
	//List<Adoptions> findByOwner(Integer ownerId);
	
	void deleteById(int id);
	
	List<Adoptions> findAll();
	
	Optional<Adoptions> findById(int id);
	
	

}
