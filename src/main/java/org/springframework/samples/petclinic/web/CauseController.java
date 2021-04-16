package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CausesService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CauseController {

	private final CausesService causeService;

	@Autowired
	public CauseController(CausesService causeService) {
		this.causeService = causeService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value="/causes/new")
	public String initCreationForm(Map<String,Object> model) {
		Cause cause = new Cause();
		model.put("cause", cause);
		return "causes/createOrUpdateCausesForm";
	}
	
	
	@PostMapping(value="/causes/new")
	public String processCreationForm(@Valid Cause cause, BindingResult result) {
		if(result.hasErrors()) {
			return "causes/createOrUpdateCausesForm";
		}else {
			this.causeService.saveCause(cause);
			
			return "redirect:/causes/" + cause.getId();
		}
	}
	
	@GetMapping(value="/causes/find")
	public String initFindForm(Map<String,Object> model) {
		model.put("cause", new Cause());
		return "causes/findCauses";
	}
	
	@GetMapping(value="/causes")
	public String processFindForm(Cause cause, BindingResult result,Map<String, Object> model) {
		if(cause.getName() == null) {
			cause.setName("");
		}
		Collection<Cause> results = this.causeService.findCauseByName(cause.getName());
		if(results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return "causes/findCauses";
		}
		else if (results.size() == 1) {
			cause = results.iterator().next();
			return "redirect:/causes/" + cause.getId();
		}
		else {
			model.put("selections", results);
			return "causes/causesList";
		}
		
	}
		
	@GetMapping("/causes/{causesId}")
	public ModelAndView showOwner(@PathVariable("causesId") int causesId) {
		ModelAndView mav = new ModelAndView("causes/causesDetails");
		mav.addObject(this.causeService.findCause(causesId));
		return mav;
	}
}
