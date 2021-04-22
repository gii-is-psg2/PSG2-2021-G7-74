/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.service.exceptions;

/**
 *
 * @author japarejo
 */
@SuppressWarnings("serial")
public class DuplicatedPetNameException extends Exception{
    
	
	public DuplicatedPetNameException() {
		super();
	}
	public DuplicatedPetNameException(String message) {
		super(message);
	}

}
