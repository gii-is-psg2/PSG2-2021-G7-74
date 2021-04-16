package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer> {

	Cause findById(Integer id) ;
		
	void save(Cause cause);
	
	Collection<Cause> findAll();
	
	void deleteById(Integer id);
	
	@Query("SELECT DISTINCT cause FROM Cause cause  WHERE cause.name LIKE %:name%")
	public Collection<Cause> findByName(@Param("name") String name);
	
}
