package org.springframework.samples.petclinic.web;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CausesService;
import org.springframework.samples.petclinic.service.DonationsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donation/{causeId}")
public class DonationsController {
	
	private final DonationsService donationService;
	private final CausesService causesService;

	@Autowired
	public DonationsController(DonationsService donationService,CausesService causeService) {
		this.donationService = donationService;
		this.causesService = causeService;
	}
	
	@ModelAttribute("cause")
	public Cause findCause(@PathVariable("causeId") int causeId) {
		return this.causesService.findCause(causeId);
	}
	@InitBinder("cause")
	public void initCauseBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("donation")
	public void initDonationBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value="donation/new")
	public String initCreationForm(Cause cause, Map<String,Object> model) {
		Donation donation = new Donation();
		cause.addDonation(donation);
		model.put("donation", donation);
		return "donation/createOrUpdateDonationForm";
	}

	@PostMapping(value="donation/new")
	public String processCreationForm(Cause cause,@Valid Donation donation, BindingResult result) {
		if(result.hasErrors()) {
			return "donation/createOrUpdateDonationForm";
		}else {
			cause.addDonation(donation);
			this.donationService.saveDonation(donation);
			return "redirect:/causes/" + cause.getId();
		}
	}


}

