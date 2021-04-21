package org.springframework.samples.petclinic.service.exceptions;

@SuppressWarnings("serial")
public class BusyBookException extends Exception{

	public BusyBookException() {
		super();
	}
	public BusyBookException(String message) {
		super(message);
	}
}
