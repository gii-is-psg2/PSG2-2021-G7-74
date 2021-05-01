package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	@Autowired
	private OwnerService ownerService;
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	
		try {
			Owner loggedOwner = this.ownerService.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		    model.put("owner", loggedOwner);
		} catch(ClassCastException e) {
			 Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, "Can`t obtain UserDetails: User not logged in");
		}
		
		return "welcome";
	  }
}
