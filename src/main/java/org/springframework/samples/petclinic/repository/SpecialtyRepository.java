package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Specialty;

public interface SpecialtyRepository extends Repository<Specialty, Integer>{
	
	Specialty findByName(String name);
}
