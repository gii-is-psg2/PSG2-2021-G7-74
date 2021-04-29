package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Import(SecurityConfiguration.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
public class DonationServiceTests {
	
	@Autowired
	protected DonationsService donationService;
	
	@Autowired
	protected CausesService causesService;
	
	@BeforeAll
	void createTestCause() {
		
		Cause cause = new Cause();
		cause.setBudgetTarget(1000.);
		cause.setCauseActive(true);
		cause.setDescription("Test cause");
		cause.setName("Test cause");
		cause.setOrganization("Test cause");
		cause.setDonations(new HashSet<Donation>());
		
		this.causesService.saveCause(cause);
	}
	
	@Test
	@Transactional
	@DisplayName("Nueva donacion - caso positivo")
	void shouldSaveCause() {
		Cause cause = ((List<Cause>) this.causesService.findCauseByName("Test cause")).get(0);
		
		Donation d = new Donation();
		d.setAmount(1000.);
		d.setClient("Test donation");
		d.setDonationDate(LocalDate.now());
		
		cause.addDonation(d);
		
		List<Donation> donationsDB = new ArrayList<Donation>(this.causesService.findCause(cause.getId()).getDonations());
		
		assertThat(donationsDB.size()).isEqualTo(1);
		assertThat(donationsDB.get(0).equals(d));
	}
	
}
