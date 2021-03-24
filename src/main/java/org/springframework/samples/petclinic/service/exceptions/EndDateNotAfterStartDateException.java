package org.springframework.samples.petclinic.service.exceptions;

@SuppressWarnings("serial")
public class EndDateNotAfterStartDateException extends Exception{
	
	public EndDateNotAfterStartDateException() {
		super();
	}
	public EndDateNotAfterStartDateException(String message) {
		super(message);
	}

}
