package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.HotelBook;
import org.springframework.samples.petclinic.repository.HotelBookRepository;
import org.springframework.samples.petclinic.service.exceptions.BusyBookException;
import org.springframework.samples.petclinic.service.exceptions.EndDateNotAfterStartDateException;
import org.springframework.stereotype.Service;

@Service
public class HotelBookService {

	@Autowired
	private HotelBookRepository hotelBookRepository;

	public void saveHotelBook(HotelBook hotelBook)
			throws DataAccessException, EndDateNotAfterStartDateException, BusyBookException {

		LocalDate fechaInicio = hotelBook.getStartDate();
		LocalDate fechaFin = hotelBook.getEndDate();

		List<HotelBook> books = hotelBookRepository.findByPetId(hotelBook.getPet().getId());

		Boolean ocupado = books.stream()
				.anyMatch(x -> ((x.getStartDate().isAfter(fechaInicio) || x.getStartDate().isEqual(fechaInicio))
						&& (x.getStartDate().isBefore(fechaFin) || x.getStartDate().isEqual(fechaFin)))
						|| ((x.getStartDate().isBefore(fechaInicio) || x.getStartDate().isEqual(fechaInicio))
								&& (x.getEndDate().isAfter(fechaFin) || x.getStartDate().isEqual(fechaFin)))
						|| ((x.getEndDate().isAfter(fechaInicio) || x.getEndDate().isEqual(fechaInicio))
								&& (x.getEndDate().isBefore(fechaFin) || x.getEndDate().isEqual(fechaFin))));

		if (fechaFin != null && fechaInicio.isAfter(fechaFin)) {
			throw new EndDateNotAfterStartDateException(
					"La fecha fin debe ser posterior o igual a la fecha de inicio!");
		} else if (fechaInicio.isAfter(fechaFin) || fechaInicio.isEqual(fechaFin)) {
			throw new EndDateNotAfterStartDateException("La fecha fin debe ser posterior a la fecha de inicio!");
		}
		if (fechaFin != null && ocupado) {
			throw new BusyBookException("Ya existe una reserva para esas fechas!");
		}

		hotelBookRepository.save(hotelBook);
	}

	public List<HotelBook> listHotelBookByPetId(int petId) throws DataAccessException {
		return hotelBookRepository.findByPetId(petId);
	}
}
