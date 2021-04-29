/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@Import(SecurityConfiguration.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class VetServiceTests {

	@Autowired
	protected VetService vetService;	

	@Test
	void shouldFindVets() {
		Collection<Vet> vets = this.vetService.findVets();

		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		assertThat(vet.getLastName()).isEqualTo("Douglas");
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}
	
	@Test
	@Transactional
	void shouldDeleteVet() {
		this.vetService.deleteVetById(1);
		
		Collection<Vet> vets = this.vetService.findVets();
		assertThrows(ObjectRetrievalFailureException.class, () -> EntityUtils.getById(vets, Vet.class, 1));
	}
	
	@Test
	@Transactional
	void shouldInsertVet() {
		Collection<Vet> vets = this.vetService.findVets();
		int found = vets.size();

		Vet vet = new Vet();
		vet.setFirstName("Sam");
		vet.setLastName("Example");
		
	        User user=new User();
	        user.setUsername("VetExample");
	        user.setPassword("1234");
	        user.setEnabled(true);
	        
	        Authorities auth = new Authorities();
            auth.setUser(user);
            auth.setAuthority("veterinarians");
                
		this.vetService.saveVet(vet);
		assertThat(vet.getId().longValue()).isNotEqualTo(0);

		vets = this.vetService.findVets();
		assertThat(vets.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	void shouldUpdateVet() {
		Vet vet = this.vetService.findVetById(1);
		String oldLastName = vet.getLastName();
		String newLastName = oldLastName + "X";

		vet.setLastName(newLastName);
		this.vetService.saveVet(vet);

		// retrieving new name from database
		vet = this.vetService.findVetById(1);
		assertThat(vet.getLastName()).isEqualTo(newLastName);
	}


}
