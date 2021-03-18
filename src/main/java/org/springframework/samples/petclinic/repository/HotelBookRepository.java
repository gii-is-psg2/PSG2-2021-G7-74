package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.HotelBook;

public interface HotelBookRepository {

	void save(HotelBook hotelBook) throws DataAccessException;
	
	List<HotelBook> findByPetId(Integer petId);
}
