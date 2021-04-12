package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.HotelBookRepository;
import org.springframework.samples.petclinic.service.HotelBookService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers=HotelBookController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class HotelBookControllerTests {

	private static final int OWNER_ID_TEST = 2;
	
	private static final int PET_ID_TEST = 2;
	
	@MockBean
	private HotelBookService hotelService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private HotelBookRepository hotelBookRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@BeforeEach
	void setup() {
		
		given(this.petService.findPetById(PET_ID_TEST)).willReturn(new Pet());
		given(this.ownerService.findOwnerById(OWNER_ID_TEST)).willReturn(new Owner());
		given(this.hotelBookRepository.findByPetId(1)).willReturn((List<HotelBook>) new ArrayList<HotelBook>());
		
	}
	
	@WithMockUser(value="spring")
	@Test
	void testInitNewHotelBookForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/hotelBooks/new",PET_ID_TEST))
		.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdateHotelBookForm"));
	}
	
	@WithMockUser(value="spring")
	@Test
	void testProcessNewHotelBookFormSuccess() throws Exception{
		mockMvc.perform(post("/owners/*/pets/{petId}/hotelBooks/new",PET_ID_TEST)
				.param("startDate", "2020/10/21")
				.param("endDate", "2021/10/21")
				.with(csrf()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@WithMockUser(value="spring")
	@Test
	void testProcessNewHotelBookFormHasErrors() throws Exception{
		mockMvc.perform(post("/owners/*/pets/{petId}/hotelBooks/new",PET_ID_TEST)
				.with(csrf()))			
		.andExpect(model().attributeHasErrors("hotelBook")).andExpect(status().isOk())
		.andExpect(view().name("pets/createOrUpdateHotelBookForm"));
	}
	
	@WithMockUser(value="spring")
	@Test
	void testShowHotelBooks() throws Exception{
		mockMvc.perform(get("/owners/*/pets/{petId}/hotelBooks",PET_ID_TEST))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("hotelBook"))
		.andExpect(view().name("hotelBookList"));
	}
}
