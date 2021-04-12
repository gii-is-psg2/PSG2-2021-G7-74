package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer> {

	Cause findById(Integer id) ;
	
	void save(Cause cause);
	
	Collection<Cause> findAll();
	
	void deleteById(Integer id);
	
}
