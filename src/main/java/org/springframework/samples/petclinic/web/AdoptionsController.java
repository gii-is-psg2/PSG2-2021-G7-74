package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoptions;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.service.AdoptionsService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAdoptionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	private final PetService petService;
	
	@Autowired
	public AdoptionsController(AdoptionsService adoptionService, OwnerService ownerService, PetService petService) {
		this.adoptionService = adoptionService;
		this.ownerService = ownerService;
		this.petService = petService;
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
	
	@GetMapping("/pets/{petId}")
	public String initAdoptionsList(@PathVariable("petId") int petId, ModelMap model) {
		Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return "pets/petAdoptionsList";
	}
	
	@GetMapping("/{adoptionId}/accept")
	public String acceptRequest(@PathVariable("adoptionId") int adoptionId) {
		//accept the request
		Adoptions adoption = this.adoptionService.findAdoptionById(adoptionId);
		adoption.setStatus(Status.ACEPTADA);
		
		//change the pet owner
		Pet pet = adoption.getPet();
		pet.setAdoptable(false);
		Owner oldOwner = pet.getOwner();
		Owner newOwner = adoption.getApplicant();
		
		oldOwner.removePet(pet);
		newOwner.addPet(pet);
		
		//save changes
		try {
			this.adoptionService.save(adoption);
		} catch (DuplicatedAdoptionException e) {
			e.printStackTrace();
		}
		
		return "redirect:/owners/"+ oldOwner.getId();
	}
	
	@GetMapping("/{adoptionId}/deny")
	public String denyRequest(@PathVariable("adoptionId") int adoptionId) {
		//deny the request
		Adoptions adoption = this.adoptionService.findAdoptionById(adoptionId);
		adoption.setStatus(Status.DENEGADA);
		
		//save changes
		try {
			this.adoptionService.save(adoption);
		} catch (DuplicatedAdoptionException e) {
			e.printStackTrace();
		}
		
		return "redirect:/owners/"+ adoption.getPet().getOwner().getId() +"/adoptions/pets/" + adoption.getPet().getId();
	}
}
