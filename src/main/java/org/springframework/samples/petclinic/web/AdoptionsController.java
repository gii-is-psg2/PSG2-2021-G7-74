package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.naming.OperationNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
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
import org.springframework.web.bind.annotation.PostMapping;

@Controller
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
	
	@GetMapping("/owners/{ownerId}/adoptions/{adoptionId}/delete")
	public String deleteById(@PathVariable("ownerId") int ownerId, @PathVariable("adoptionId") int adoptionId) throws OperationNotSupportedException {
		Owner loggedOwner = this.ownerService.getLoggedOwner();
		
		if(ownerId == loggedOwner.getId()) {
			Adoption adoption = adoptionService.findAdoptionById(adoptionId);
			Owner own = ownerService.findOwnerById(ownerId);
			Pet pet = adoption.getPet();
			
			pet.removeAdoption(adoption);
	    	own.removeAdoption(adoption);
			this.adoptionService.deleteAdoptionById(adoptionId);
			return "redirect:/owners/"+ownerId;
		} else {
			throw new OperationNotSupportedException("You cannot delete other user's requests");
		}
	}
	
	@GetMapping("/owners/{ownerId}/adoptions/pets/{petId}")
	public String initAdoptionsList(@PathVariable("petId") int petId, ModelMap model, @PathVariable("ownerId") int ownerId) throws OperationNotSupportedException {
		Owner loggedOwner = this.ownerService.getLoggedOwner();
		
		if(ownerId == loggedOwner.getId()) {

			Pet pet = this.petService.findPetById(petId);
			model.put("pet", pet);
			return "pets/petAdoptionsList";
		} else {
			throw new OperationNotSupportedException("You cannot access other user's adoptions requests");
		}
	}
	
	@GetMapping("/owners/{ownerId}/adoptions/{adoptionId}/accept")
	public String acceptRequest(@PathVariable("adoptionId") int adoptionId) {
		//accept the request
		Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
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
			 Logger.getLogger(AdoptionsController.class.getName()).log(Level.SEVERE, e.getMessage());
		}
		
		//deny the rest of requests
		pet.getAdoptions().forEach(a -> {
			if(a.getStatus().equals(Status.EN_PROCESO)) {
				a.setStatus(Status.DENEGADA);
				try {
					this.adoptionService.save(a);
				} catch(Exception e) {
					 Logger.getLogger(AdoptionsController.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
		});
		
		return "redirect:/owners/"+ oldOwner.getId();
	}
	
	@GetMapping("/owners/{ownerId}/adoptions/{adoptionId}/deny")
	public String denyRequest(@PathVariable("adoptionId") int adoptionId) {
		//deny the request
		Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
		adoption.setStatus(Status.DENEGADA);
		
		//save changes
		try {
			this.adoptionService.save(adoption);
		} catch (DuplicatedAdoptionException e) {
			 Logger.getLogger(AdoptionsController.class.getName()).log(Level.SEVERE, e.getMessage());
		}
		
		return "redirect:/owners/"+ adoption.getPet().getOwner().getId() +"/adoptions/pets/" + adoption.getPet().getId();
	}
	
	@GetMapping("/adoptions")
	public String showList(ModelMap model) {
		Owner loggedOwner = this.ownerService.getLoggedOwner();
		
		model.put("today", LocalDate.now());
		model.put("adoptablePet", 
				this.petService.findAll().stream()
					.filter(p -> loggedOwner.getAdoptions().stream().noneMatch(a->a.getPet().equals(p)) 
							&& p.getOwner().getId() != null && !p.getOwner().getId().equals(loggedOwner.getId()) 
							&& p.getAdoptable())
					.collect(Collectors.toList()));
		return "adoptions/adoptionList";
	}
	
	@PostMapping("/adoptions")
	public String newAdoption(Adoption adoption, int petId) {
		Owner loggedOwner = this.ownerService.getLoggedOwner();
		Pet pet = this.petService.findPetById(petId);
		
		adoption.setDate(LocalDate.now());
		
		pet.addAdoption(adoption);
		loggedOwner.addAdoption(adoption);
		
		if(pet != null && loggedOwner != null) {
			try {
				this.adoptionService.save(adoption);
			} catch (DuplicatedAdoptionException e) {
				 Logger.getLogger(AdoptionsController.class.getName()).log(Level.SEVERE, e.getMessage());
			}
		}
		return "redirect:/adoptions";
	}
}
