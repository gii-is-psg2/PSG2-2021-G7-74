package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(SecurityConfiguration.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CausesServiceTests {
	
	@Autowired
	protected CausesService causesService;
	
	@Test
	@Transactional
	@DisplayName("Nueva causa - caso positivo")
	void shouldSaveCause() {
		Donation d = new Donation();
		d.setAmount(1000.);
		d.setClient("Test donation");
		
		Cause cause = new Cause();
		cause.setBudgetTarget(1000.);
		cause.setCauseActive(true);
		cause.setDescription("Test cause");
		cause.setName("Test cause");
		cause.setOrganization("Test cause");
		cause.addDonation(d);
		
		this.causesService.saveCause(cause);
		
		assertThat(causesService.findCauseByName("Test cause").size()).isGreaterThan(0);
	}
	
}
