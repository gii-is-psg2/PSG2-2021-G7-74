package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.model.BaseEntity;


public interface HotelBookRepository extends Repository<HotelBook, Integer> {

	void save(HotelBook hotelBook) throws DataAccessException;
	
	List<HotelBook> findByPetId(Integer petId);
}
