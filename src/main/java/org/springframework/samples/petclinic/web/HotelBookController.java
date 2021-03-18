/*package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.HotelBookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
	
@Controller
public class HotelBookController {
		
	private final HotelBookService hotelBookService;
		
	@Autowired
	public HotelBookController(HotelBookService hotelBookService) {
		this.hotelBookService = hotelBookService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	


}*/
