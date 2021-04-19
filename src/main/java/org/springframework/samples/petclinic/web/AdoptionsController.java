package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AdoptionsService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners/{ownerId}/adoptions")
public class AdoptionsController {
	
	private final AdoptionsService adoptionService;
	private final OwnerService ownerService;
	
	@Autowired
	public AdoptionsController(AdoptionsService adoptionService, OwnerService ownerService) {
		this.adoptionService = adoptionService;
		this.ownerService = ownerService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
//	
//	@ModelAttribute("adoptions")
//	public Adoptions loadOwnerWithAdoptions(@PathVariable("ownerId") int ownerId) {
//		Owner owner = this.ownerService.findOwnerById(ownerId);
//		Adoptions adoption = new Adoptions();
//		owner.addAdoption(adoption);
//		return adoption;
//	}
//	
	@GetMapping("/{adoptionId}/delete")
	public String deleteById(@PathVariable("ownerId") int ownerId, @PathVariable("adoptionId") int adoptionId) {
		Owner own = ownerService.findOwnerById(ownerId);
    	own.removeAdoption(adoptionService.findAdoptionById(adoptionId));
		this.adoptionService.deleteAdoptionById(adoptionId);
		return "redirect:/owners/{ownerId}";
	}	
}
