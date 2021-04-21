package org.springframework.samples.petclinic.service.exceptions;

public class DuplicatedAdoptionException extends Exception{
	
	public DuplicatedAdoptionException() {
		super();
	}
	public DuplicatedAdoptionException(String message) {
		super(message);
	}

}
