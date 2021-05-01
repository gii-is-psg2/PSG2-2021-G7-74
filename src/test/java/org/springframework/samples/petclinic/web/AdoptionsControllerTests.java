package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.service.AdoptionsService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers=AdoptionsController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class AdoptionsControllerTests {

	private static final int OWNER_ID_TEST = 2;
	
	private static final int APPLICANT_ID_TEST = 3;
	
	private static final int APPLICANT_ID_TEST_2 = 4;

	private static final int APPLICANT_ID_TEST_3 = 5;
	
	private static final int PET_ID_TEST = 2;
	
	private static final int ADOPTION_ID_TEST = 2;
	
	private static final int ADOPTION_ID_TEST_2 = 3;

	private static final int ADOPTION_ID_TEST_3 = 3;
	
	private static final int PETTYPE_ID_TEST = 2;

	
	@MockBean
	private AdoptionsService adoptionsService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private PetService petService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Owner george;
	
	private Owner paco;
	
	private Owner jesus;
	
	private Owner jose;

	private Adoption adopcion;
	
	private Adoption adopcion2;

	private Adoption adopcion3;

	private List<Adoption> adoptionsList;
	
	private Pet pet;
		
	@BeforeEach
	void setup() {
		
		//Owner de la mascota
		george = new Owner();
		george.setId(OWNER_ID_TEST);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");
		
		//Solicitante 1
		paco = new Owner();
		paco.setId(APPLICANT_ID_TEST);
		paco.setFirstName("Paco");
		paco.setLastName("Rondan");
		paco.setAddress("118 W. Liberty St.");
		paco.setCity("Marbella");
		paco.setTelephone("6085251023");
		
		//Solicitante 2
		jesus = new Owner();
		jesus.setId(APPLICANT_ID_TEST_2);
		jesus.setFirstName("Jesus");
		jesus.setLastName("Alvarez");
		jesus.setAddress("18 W. Liberty St.");
		jesus.setCity("Sevilla");
		jesus.setTelephone("7085251023");

		//Solicitante 3
		jose = new Owner();
		jose.setId(APPLICANT_ID_TEST_3);
		jose.setFirstName("Jose");
		jose.setLastName("Toledo");
		jose.setAddress("18 W. Freedom St.");
		jose.setCity("Vigo");
		jose.setTelephone("7085250093");
		
		//Mascota
		pet = new Pet();
		pet.setId(PET_ID_TEST);
		pet.setAdoptable(true);
		pet.setBirthDate(LocalDate.now().minusYears(2));
		pet.setName("Florentino");
		PetType type = new PetType();
		type.setName("cat");
		type.setId(PETTYPE_ID_TEST);
		pet.setType(type);
		george.addPet(pet);
		
		//Adopcion 1
		adopcion = new Adoption();
		adopcion.setId(ADOPTION_ID_TEST);
		adopcion.setDate(LocalDate.now().minusDays(1));
		adopcion.setDescription("Hey, i will take care of your little pet");
		george.addAdoption(adopcion);
		pet.addAdoption(adopcion);
		adopcion.setApplicant(paco);
		adopcion.setStatus(Status.EN_PROCESO);
		
		//Adopcion 2
		adopcion2 = new Adoption();
		adopcion2.setId(ADOPTION_ID_TEST_2);
		adopcion2.setDate(LocalDate.now().minusDays(1));
		adopcion2.setDescription("I would pleasure to adopt your pet");
		george.addAdoption(adopcion2);
		pet.addAdoption(adopcion2);
		adopcion2.setApplicant(jesus);
		adopcion2.setStatus(Status.EN_PROCESO);
		
		//Adopcion 3
		adopcion3 = new Adoption();
		adopcion3.setId(ADOPTION_ID_TEST_3);
		adopcion3.setDate(LocalDate.now().minusDays(1));
		adopcion3.setDescription("I'm in love with your pet, let me be his new owner!!");
		george.addAdoption(adopcion3);
		pet.addAdoption(adopcion3);
		adopcion3.setApplicant(jose);
		adopcion3.setStatus(Status.EN_PROCESO);

		adoptionsList = new ArrayList<Adoption>();
		adoptionsList.add(adopcion);
		adoptionsList.add(adopcion2);
		adoptionsList.add(adopcion3);
		
	}
	
	
	@WithMockUser(value="spring")
	@Test
	void testDeleteById() throws Exception {
		
		given(this.ownerService.findOwnerById(OWNER_ID_TEST)).willReturn(george);
		given(this.ownerService.getLoggedOwner()).willReturn(george);
		given(this.adoptionsService.findAdoptionById(ADOPTION_ID_TEST)).willReturn(adopcion);
		
		Integer numAdoptionsOwner = george.getAdoptions().size();
		Integer numAdoptionsPet = pet.getAdoptions().size();
		
		mockMvc.perform(get("/owners/{ownerId}/adoptions/{adoptionId}/delete",OWNER_ID_TEST,ADOPTION_ID_TEST)
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/"+ OWNER_ID_TEST));
				
		//Comprobamos que ha sido borrada tanto del owner como de la mascota
		assertThat(george.getAdoptions().size()).isEqualTo(numAdoptionsOwner-1);
		assertThat(pet.getAdoptions().size()).isEqualTo(numAdoptionsPet-1);		
	}
	
	@WithMockUser(value="spring")
	@Test
	void testDeleteByIdNotOwner() throws Exception {
		
		given(this.ownerService.findOwnerById(APPLICANT_ID_TEST)).willReturn(george);
		given(this.ownerService.getLoggedOwner()).willReturn(paco);
		given(this.adoptionsService.findAdoptionById(ADOPTION_ID_TEST)).willReturn(adopcion);
		
		MvcResult resultException = mockMvc.perform(get("/owners/{ownerId}/adoptions/{adoptionId}/delete",OWNER_ID_TEST,ADOPTION_ID_TEST)
				.with(csrf()))
				.andExpect(status().isOk())
		.andExpect(view().name("exception"))
		.andReturn();
				
		//Vemos que la adopcion no puede ser borrada por otro owner
		assertThat(resultException.getResolvedException().getMessage()).isEqualTo("You cannot delete other user's requests");

	}
	
	@WithMockUser(value="spring")
	@Test
	void testInitAdoptionsList() throws Exception {
		
		given(this.ownerService.findOwnerById(OWNER_ID_TEST)).willReturn(george);
		given(this.ownerService.getLoggedOwner()).willReturn(george);
		given(this.petService.findPetById(PET_ID_TEST)).willReturn(pet);

		mockMvc.perform(get("/owners/{ownerId}/adoptions/pets/{petId}",OWNER_ID_TEST,PET_ID_TEST))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("pet"))
		.andExpect(view().name("pets/petAdoptionsList"));
	}
	
	@WithMockUser(value="spring")
	@Test
	void testInitAdoptionsListNotOwner() throws Exception {
		
		given(this.ownerService.findOwnerById(APPLICANT_ID_TEST)).willReturn(george);
		given(this.ownerService.getLoggedOwner()).willReturn(paco);

		MvcResult resultException = mockMvc.perform(get("/owners/{ownerId}/adoptions/pets/{petId}",OWNER_ID_TEST,PET_ID_TEST))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"))
		.andReturn();
		
		assertThat(resultException.getResolvedException().getMessage()).isEqualTo("You cannot access other user's adoptions requests");

	}
	
	@WithMockUser(value="spring")
	@Test
	void testAcceptRequest() throws Exception {
		
		given(this.petService.findPetById(PET_ID_TEST)).willReturn(pet);
		given(this.adoptionsService.findAdoptionById(ADOPTION_ID_TEST)).willReturn(adopcion);
		
		mockMvc.perform(get("/owners/{ownerId}/adoptions/{adoptionId}/accept",OWNER_ID_TEST,ADOPTION_ID_TEST))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/"+ OWNER_ID_TEST));
		
		//La mascota ya no esta en adopcion
		assertThat(adopcion.getPet().getAdoptable()).isFalse();
		//Vemos que ha cambiado de dueño
		assertThat(pet.getOwner().getFirstName()).isEqualTo(adopcion.getApplicant().getFirstName());
		assertThat(pet.getOwner().getLastName()).isEqualTo(adopcion.getApplicant().getLastName());
		//Comprobamos que el antiguo dueño ya no tiene esa mascota
		assertThat(pet.getOwner().getFirstName()).isNotEqualTo(george.getFirstName());
		assertThat(pet.getOwner().getLastName()).isNotEqualTo(george.getLastName());
		//Vemos que se han rechazado el resto de solicitudes
		assertThat(adopcion2.getStatus()).isEqualTo(Status.DENEGADA);
		assertThat(adopcion3.getStatus()).isEqualTo(Status.DENEGADA);
		//Comprobamos que se ha guardado correctamente la adopcion
		assertThat(adopcion.getStatus()).isEqualTo(Status.ACEPTADA);


	}
	
	@WithMockUser(value="spring")
	@Test
	void testDenyRequest() throws Exception {
		
		given(this.petService.findPetById(PET_ID_TEST)).willReturn(pet);
		given(this.adoptionsService.findAdoptionById(ADOPTION_ID_TEST_2)).willReturn(adopcion2);
		
		mockMvc.perform(get("/owners/{ownerId}/adoptions/{adoptionId}/deny",OWNER_ID_TEST,ADOPTION_ID_TEST_2))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/"+ OWNER_ID_TEST+"/adoptions/pets/" + PET_ID_TEST));
		
		//La mascota sigue en adopcion
		assertThat(adopcion.getPet().getAdoptable()).isTrue();
		//Vemos que no ha cambiado de dueño
		assertThat(pet.getOwner().getFirstName()).isNotEqualTo(adopcion2.getApplicant().getFirstName());
		assertThat(pet.getOwner().getLastName()).isNotEqualTo(adopcion2.getApplicant().getLastName());
		assertThat(pet.getOwner().getFirstName()).isEqualTo(george.getFirstName());
		assertThat(pet.getOwner().getLastName()).isEqualTo(george.getLastName());
		//Vemos que se persisten el resto de solicitudes
		assertThat(adopcion.getStatus()).isEqualTo(Status.EN_PROCESO);
		assertThat(adopcion3.getStatus()).isEqualTo(Status.EN_PROCESO);
		//Comprobamos que la adopcion ha sido denegada
		assertThat(adopcion2.getStatus()).isEqualTo(Status.DENEGADA);


	}
	
	@WithMockUser(value="spring")
	@Test
	void testShowList() throws Exception {
		
		given(this.ownerService.findByUsername(any(String.class))).willReturn(george);
	
		mockMvc.perform(get("/adoptions"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("adoptablePet"))
		.andExpect(view().name("adoptions/adoptionList"));
	}

	@WithMockUser(value="spring")
	@Test
	void testNewAdoption() throws Exception {
		
		given(this.ownerService.getLoggedOwner()).willReturn(paco);
		given(this.petService.findPetById(PET_ID_TEST)).willReturn(pet);
		
		mockMvc.perform(post("/adoptions")
				.with(csrf())
				.param("petId",adopcion.getPet().getId().toString())
				.param("status", Status.EN_PROCESO.toString())
				.param("description", adopcion.getDescription()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/adoptions"));
		
	
		//Vemos que se ha añadido correctamente la adopcion
		assertThat(adopcion.getApplicant().getFirstName()).isEqualTo(paco.getFirstName());
		assertThat(adopcion.getApplicant().getLastName()).isEqualTo(paco.getLastName());
		

	}

	
}
