package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedAdoptionException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Import(SecurityConfiguration.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdoptionsServiceTests {

	@Autowired
	protected AdoptionsService adoptionsService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected UserService userService;
	
	@Test
	@Transactional
	@DisplayName("Nueva Adopcion -- caso positivo")
	void shouldSaveAdoption() throws DataAccessException, DuplicatedPetNameException {
		
		//Creamos el solicitante
		Owner applicant = new Owner();
		applicant.setFirstName("Antonio");
		applicant.setLastName("Padilla");
		applicant.setAddress("4, Avenida Jerez");
		applicant.setCity("Jerez");
		applicant.setTelephone("4444444444");
		applicant.setId(88);

        //Creamos el usuario del solicitante
  		User user1=new User();
  		user1.setUsername("antuan");
  		user1.setPassword("supersecretpassword");
  		user1.setEnabled(true);
  		applicant.setUser(user1);  
		ownerService.saveOwner(applicant);

  		
		//Creamos el owner de la mascota
        Owner owner = this.ownerService.findOwnerById(6);
		
        
		// Creamos la mascota
		Pet pet = new Pet();
		pet.setName("Antonio Jose");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now().minusYears(2));
		pet.setAdoptable(true);
		owner.addPet(pet);
		petService.savePet(pet);

		// Creamos la adopcion
		Adoption adop = new  Adoption();
		adop.setDate(LocalDate.now().minusDays(1));
		adop.setDescription("I want to take care of your pet!");
		adop.setStatus(Status.EN_PROCESO);
		applicant.addAdoption(adop);
		pet.addAdoption(adop);
		
		try {
			adoptionsService.save(adop);
		} catch (DataAccessException e) {
            Logger.getLogger(AdoptionsServiceTests.class.getName()).log(Level.SEVERE, null, e);
		} catch (DuplicatedAdoptionException e) {
            Logger.getLogger(AdoptionsServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}
		assertThat(adop.getPet().getName()).isEqualTo("Antonio Jose");
		assertThat(adop.getApplicant().getFirstName()).isEqualTo("Antonio");
		assertThat(adop.getStatus()).isEqualTo(Status.EN_PROCESO);
		assertThat(applicant.getAdoptions().get(0).getPet().getName()).isEqualTo("Antonio Jose");
		assertThat(pet.getAdoptions().get(0).getApplicant().getLastName()).isEqualTo("Padilla");
		assertThat(pet.getAdoptions().get(0).getPet().getOwner().getLastName()).isEqualTo("Coleman");
	}
	
	@Test
	@Transactional
	@DisplayName("Nueva Adopcion Duplicada -- caso negativo")
	void shouldNotSaveAdoptionDuplicateAdoption() throws DataAccessException, DuplicatedPetNameException, DuplicatedAdoptionException {
		
		//Creamos el solicitante
		Owner applicant = ownerService.findOwnerById(1);

		// Creamos la mascota
		Pet pet = petService.findPetById(12);
				
		// Creamos la primera solicitud de adopcion
		Adoption adop = adoptionsService.findAdoptionById(1);

		// Creamos la segunda solicitud de adopcion replicada
		Adoption adop1 =  new  Adoption();
		adop1.setDate(adop.getDate());
		adop1.setDescription(adop.getDescription());
		adop1.setStatus(adop.getStatus());
		adop1.setApplicant(applicant);
		adop1.setPet(pet);
		
		assertThrows(DuplicatedAdoptionException.class, () -> {	
			adoptionsService.save(adop1);});
		}
	
	@Test
	@Transactional
	@DisplayName("Encontrar Adopcion Por ID")
	void shouldFindAdoptionById() {
		assertThat(adoptionsService.findAdoptionById(1).getPet().getName()).isEqualTo("Lucky");
	}
	@Test
	@Transactional
	@DisplayName("Encontrar Todas Las Adopciones")
	void shouldFindAllAdoption() {
		assertThat(adoptionsService.findAll().size()).isEqualTo(1);
	}
	@Test
	@Transactional
	@DisplayName("Eliminar Adopcion Por ID")
	void shouldDeleteAdoptionById() {
		adoptionsService.deleteAdoptionById(1);
		assertThat(adoptionsService.findAdoptionById(1)).isNull();
	}

}
