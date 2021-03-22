package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.repository.HotelBookRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelBookService {
	
	@Autowired
	private HotelBookRepository hotelBookRepository;
	
	public void saveHotelBook(HotelBook hotelBook) throws DataAccessException{
		hotelBookRepository.save(hotelBook);
	}
	
	public List<HotelBook> listHotelBookByPetId(int petId) throws DataAccessException{
		return hotelBookRepository.findByPetId(petId);
	}
}
