package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.repository.HotelBookRepository;

public class HotelBookService {
	private HotelBookRepository hotelBookRepository;
	
	public void saveHotelBook(HotelBook hotelBook) throws DataAccessException{
		hotelBookRepository.save(hotelBook);
	}
	
	public List<HotelBook> listHotelBookByPetId(int petId) throws DataAccessException{
		return hotelBookRepository.findByPetId(petId);
	}
}
