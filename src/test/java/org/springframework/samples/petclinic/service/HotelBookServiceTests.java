package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.exceptions.BusyBookException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.EndDateNotAfterStartDateException;
import org.springframework.stereotype.Service;

@Import(SecurityConfiguration.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class HotelBookServiceTests {
	
	@Autowired
	protected HotelBookService hotelBookService;
	
	@Autowired
	protected PetService petService;
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso positivo")
	void shouldSaveHotelBook() {
		
		Pet pet = new Pet();
		PetType petype = new PetType();
		petype.setName("cat");
		
		pet.setType(petype);
		pet.setName("Diego Carlos");
		pet.setBirthDate(LocalDate.now().minusYears(2));
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(LocalDate.now().minusDays(1));
		hb.setEndDate(LocalDate.now().plusDays(1));
		hb.setPet(pet);
		
		try {
			hotelBookService.saveHotelBook(hb);
		} catch (DataAccessException e) {
			 Logger.getLogger(HotelBookServiceTests.class.getName()).log(Level.SEVERE, null, e);
		} catch (EndDateNotAfterStartDateException e) {
			 Logger.getLogger(HotelBookServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}catch (BusyBookException e) {
			 Logger.getLogger(HotelBookServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}
		
		assertThat(hb.getPet().getName()).isEqualTo("Diego Carlos");

	}

	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Fecha Inicio After Fecha Fin)")
	void shouldNotSaveHotelBookFechaInicioAfterFechaFin() {
		
		Pet pet = new Pet();	
		PetType petype = new PetType();
		petype.setName("cat");
		
		pet.setType(petype);
		pet.setName("Diego Carlos");
		pet.setBirthDate(LocalDate.now().minusYears(2));
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(LocalDate.now().plusDays(1));
		hb.setEndDate(LocalDate.now());
		hb.setPet(pet);
		
		assertThrows(EndDateNotAfterStartDateException.class, () -> {hotelBookService.saveHotelBook(hb);});
		
	}
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Fecha Inicio Is Equal to Fecha Fin)")
	void shouldNotSaveHotelBookFechaInicioIsEqualFechaFin() {
		
		Pet pet = new Pet();
		
		PetType petype = new PetType();
		petype.setName("cat");
		pet.setType(petype);
		pet.setName("Diego Carlos");
		pet.setBirthDate(LocalDate.now().minusYears(2));
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(LocalDate.now());
		hb.setEndDate(LocalDate.now());
		hb.setPet(pet);
		
		assertThrows(EndDateNotAfterStartDateException.class, () -> {hotelBookService.saveHotelBook(hb);});
		
	}
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Fecha Fin Before Fecha Inicio)")
	void shouldNotSaveHotelBookFechaFinBeforeFechaInicio() {
		
		Pet pet = new Pet();	
		PetType petype = new PetType();
		petype.setName("cat");
		
		pet.setType(petype);
		pet.setName("Diego Carlos");
		pet.setBirthDate(LocalDate.now().minusYears(2));
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(LocalDate.now());
		hb.setEndDate(LocalDate.now().minusDays(1));
		hb.setPet(pet);
		
		assertThrows(EndDateNotAfterStartDateException.class, () -> {hotelBookService.saveHotelBook(hb);});
		
	}
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Ya existe una reserva para esas fechas!)")
	void shouldNotSaveHotelBookThereIsAlreadyBookForTheseDates() throws DataAccessException, EndDateNotAfterStartDateException, BusyBookException, DuplicatedPetNameException {
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(hotelBookService.listHotelBookByPetId(1).get(0).getStartDate().plusDays(2));
		hb.setEndDate(hotelBookService.listHotelBookByPetId(1).get(0).getEndDate().minusDays(1));
		hb.setPet(petService.findPetById(1));

		assertThrows(BusyBookException.class, () -> {hotelBookService.saveHotelBook(hb);});
	}
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Ya existe una reserva para esas fechas!)")
	void shouldNotSaveHotelBookThereIsAlreadyBookForTheseDatesTwo() throws DataAccessException, EndDateNotAfterStartDateException, BusyBookException, DuplicatedPetNameException {
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(hotelBookService.listHotelBookByPetId(1).get(0).getStartDate().minusDays(2));
		hb.setEndDate(hotelBookService.listHotelBookByPetId(1).get(0).getEndDate().minusDays(1));
		hb.setPet(petService.findPetById(1));

		assertThrows(BusyBookException.class, () -> {hotelBookService.saveHotelBook(hb);});
	}
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Ya existe una reserva para esas fechas!)")
	void shouldNotSaveHotelBookThereIsAlreadyBookForTheseDatesThree() throws DataAccessException, EndDateNotAfterStartDateException, BusyBookException, DuplicatedPetNameException {
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(hotelBookService.listHotelBookByPetId(1).get(0).getStartDate().plusDays(2));
		hb.setEndDate(hotelBookService.listHotelBookByPetId(1).get(0).getEndDate().plusDays(1));
		hb.setPet(petService.findPetById(1));
		
		assertThrows(BusyBookException.class, () -> {hotelBookService.saveHotelBook(hb);});
	}
	
	@Test
	@Transactional
	@DisplayName("Nuevo HotelBook -- caso negativo(Ya existe una reserva para esas fechas!)")
	void shouldNotSaveHotelBookThereIsAlreadyBookForTheseDatesFour() throws DataAccessException, EndDateNotAfterStartDateException, BusyBookException, DuplicatedPetNameException {
		
		HotelBook hb = new HotelBook();
		hb.setStartDate(hotelBookService.listHotelBookByPetId(1).get(0).getStartDate());
		hb.setEndDate(hotelBookService.listHotelBookByPetId(1).get(0).getEndDate());
		hb.setPet(petService.findPetById(1));
		
		assertThrows(BusyBookException.class, () -> {hotelBookService.saveHotelBook(hb);});
	}
	
	@Test
	@Transactional
	@DisplayName("Lista de HotelBook De Una Mascota -- caso positivo")
	void shouldListHotelBookByPetId() {
		assertThat(hotelBookService.listHotelBookByPetId(2).size()).isEqualTo(1);

	}
	
	@Test
	@Transactional
	@DisplayName("Lista de HotelBook De Una Mascota -- caso negativo(No mascota con ese ID)")
	void shouldNotListHotelBookByPetId() {
		assertThat(hotelBookService.listHotelBookByPetId(103).size()).isEqualTo(0);	
	}
	
}
