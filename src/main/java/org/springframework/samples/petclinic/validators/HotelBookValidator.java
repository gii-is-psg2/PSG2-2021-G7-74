package org.springframework.samples.petclinic.validators;


import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.HotelBookRepository;

public class HotelBookValidator implements ConstraintValidator<HotelBookConstraint, HotelBook> {
	
	private HotelBookRepository repository;
	
	
	@Autowired
    public void setClientRepository(HotelBookRepository repository){
        this.repository=repository;
    }
	
	public void initialize(HotelBookConstraint hotelBook) {
	}

	public boolean isValid(HotelBook hotel, ConstraintValidatorContext cxt) {
		boolean res = true;
		if ((hotel.getEndDate() == null || hotel.getStartDate() == null || hotel.getPet() == null)) {
			return false;
		}
		
		Pet pet = hotel.getPet();
		if(pet.getId()==null) {
			return res;
		}
		List<HotelBook> list = repository.findByPetId(pet.getId());
		for (HotelBook book : list) {
			if((hotel.getStartDate().isAfter(book.getStartDate()) 
					&& hotel.getStartDate().isBefore(book.getEndDate()))) {
				return false;
			}
			if((hotel.getEndDate().isAfter(book.getStartDate())
					&& hotel.getStartDate().isBefore(book.getEndDate()))){
				return false;
			}
			if(hotel.getStartDate().isBefore(book.getEndDate()) && 
					hotel.getEndDate().isAfter(book.getEndDate())){
				return false;
			}
			
		}

		return res;

	}
}
