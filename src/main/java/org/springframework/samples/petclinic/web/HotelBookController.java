package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.HotelBookService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.BusyBookException;
import org.springframework.samples.petclinic.service.exceptions.EndDateNotAfterStartDateException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HotelBookController {

	private final HotelBookService hotelBookService;

	private final PetService petService;

	@Autowired
	public HotelBookController(HotelBookService hotelBookService, PetService petService) {
		this.hotelBookService = hotelBookService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("hotelBook")
	public HotelBook loadPetWithHotelBook(@PathVariable("petId") int petId) {
		Pet pet = this.petService.findPetById(petId);
		HotelBook hotelBook = new HotelBook();
		pet.addHotelBook(hotelBook);
		return hotelBook;
	}

	@GetMapping(value = "/owners/*/pets/{petId}/hotelBooks/new")
	public String initNewHotelBookForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdateHotelBookForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/hotelBooks/new")
	public String processNewHotelBookForm(@Valid HotelBook hotelBook, BindingResult result){
		if (result.hasErrors()) {
			return "pets/createOrUpdateHotelBookForm";
		} else {			
			try {
				this.hotelBookService.saveHotelBook(hotelBook);
				return "redirect:/owners/{ownerId}";
			} catch (DataAccessException e) {
				e.printStackTrace();
				return "dateException";

			} catch (EndDateNotAfterStartDateException e) {
				e.printStackTrace();
				return "dateException";
			}catch (BusyBookException e) {
				e.printStackTrace();
				return "dateException";
			}
			
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/hotelBooks")
	public String showHotelBooks(@PathVariable int petId, Map<String, Object> model) {
		model.put("hotelBooks", this.petService.findPetById(petId).getHotelBooks());
		return "hotelBookList";
	}

}
