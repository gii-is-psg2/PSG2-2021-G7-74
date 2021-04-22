package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.test.web.servlet.MockMvc;
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


@WebMvcTest(controllers=DonationsController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class DonationsControllerTests {

	private static final int TEST_CAUSE_ID = 22;

	
	@MockBean
	private DonationsService donationsService;
	
	@MockBean
	private CausesService causesService;
	
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
		
		given(causesService.findCause(TEST_CAUSE_ID)).willReturn(cause);
		
		mockMvc.perform(get("/donation/{causeId}/donation/new",TEST_CAUSE_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("donation"))
		.andExpect(view().name("donation/createOrUpdateDonationForm"));
	}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
		
	given(causesService.findCause(TEST_CAUSE_ID)).willReturn(cause);

	mockMvc.perform(post("/donation/{causeId}/donation/new",TEST_CAUSE_ID)
						.with(csrf())
						.param("amount", "300")
						.param("client", "Jesus Gil"))					
			.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
		
	given(causesService.findCause(TEST_CAUSE_ID)).willReturn(cause);

	mockMvc.perform(post("/donation/{causeId}/donation/new",TEST_CAUSE_ID)
						.with(csrf())
						.param("amount", "0")
						.param("client", "Jesus Gil"))					
			.andExpect(status().isOk())
			.andExpect(view().name("donation/createOrUpdateDonationForm"));
	}






















}
