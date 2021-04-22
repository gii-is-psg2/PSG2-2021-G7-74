package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.CausesService;
import org.springframework.samples.petclinic.service.DonationsService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers=CausesController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CausesControllerTests {
	
	private static final int TEST_CAUSE_ID = 1;
	
	@MockBean
	private CausesService causeService;
	
	@MockBean
	private DonationsService donationService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Cause cause;
	
	@BeforeEach
	void setup() {

		cause = new Cause();
		cause.setName("Salvar Pandas");
		cause.setBudgetTarget(9000.);
		cause.setDescription("Su habitat esta siendo destruido");
		cause.setOrganization("WorldPandas");
		cause.setCauseActive(true);

	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/causes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("cause"))
		.andExpect(view().name("causes/createOrUpdateCausesForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
	mockMvc.perform(post("/causes/new")
						.with(csrf())
						.param("name", "Salvar a los pandas")
						.param("description", "El bambu se esta agotando, debemos ayudar al ecosistema y salvar a los pandas")
						.param("budgetTarget", "8900")
						.param("organization", "PandaWorld"))					
			.andExpect(status().is3xxRedirection());
}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationHasErrors() throws Exception {
	mockMvc.perform(post("/causes/new")
						.with(csrf())
						.param("name", "")
						.param("description", "El bambu se esta agotando, debemos ayudar al ecosistema y salvar a los pandas")
						.param("budgetTarget", "0")
						.param("organization", "PandaWorld"))					
			.andExpect(status().isOk())
			.andExpect(view().name("causes/createOrUpdateCausesForm"));
	}
	@WithMockUser(value = "spring")
    @Test
    void testInitFindForm() throws Exception {
	mockMvc.perform(get("/causes/find"))
	.andExpect(status().isOk())
	.andExpect(model().attributeExists("cause"))
			.andExpect(view().name("causes/findCauses"));
	}
    
	@WithMockUser(value = "spring")
    @Test
    void testProcessFindFormSuccesZeroCauses() throws Exception {
    	given(this.causeService.findCauseByName("Lince")).willReturn(Lists.newArrayList());
    	
    	mockMvc.perform(get("/causes")
						.param("name","Lince"))						
			.andExpect(status().isOk())
			.andExpect(view().name("causes/findCauses"));
    }
    
	@WithMockUser(value = "spring")
    @Test
    void testProcessFindFormSuccesOneCause() throws Exception {
    	given(this.causeService.findCauseByName("Lince")).willReturn(Lists.newArrayList(cause));
    	
    	mockMvc.perform(get("/causes")
						.param("name","Lince"))						
			.andExpect(status().is3xxRedirection());
    }

	@WithMockUser(value = "spring")
    @Test
    void testProcessFindFormSuccesSelectionsCauses() throws Exception {
    	given(this.causeService.findCauseByName("Lince")).willReturn(Lists.newArrayList(cause,cause));
    	
    	mockMvc.perform(get("/causes")
						.param("name","Lince"))						
    	.andExpect(status().isOk())
    	.andExpect(model().attributeExists("selections"))
		.andExpect(view().name("causes/causesList"));
    }
    
    @WithMockUser(value = "spring")
    @Test
    void testhowCause() throws Exception {
    	
    	given(this.causeService.findCause(TEST_CAUSE_ID)).willReturn(cause);
    	
    	mockMvc.perform(get("/causes/{causesId}", TEST_CAUSE_ID))
    	.andExpect(status().isOk())
		.andExpect(model().attribute("cause", hasProperty("name", is(cause.getName()))))
		.andExpect(model().attribute("cause", hasProperty("description", is(cause.getDescription()))))
		.andExpect(model().attribute("cause", hasProperty("budgetTarget", is(cause.getBudgetTarget()))))
		.andExpect(model().attribute("cause", hasProperty("organization", is(cause.getOrganization()))))
		.andExpect(view().name("causes/causesDetails"));
    }
	
	
	
	
}
