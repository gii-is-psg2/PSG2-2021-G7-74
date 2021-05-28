package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ITopController {

	
	 private HttpHeaders getHeaders(){  
	        String plainCredentials="admin:admin";  
	        String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());  
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Authorization", "Basic " + base64Credentials);  
	        return headers;  
	    }  
	
	@GetMapping(value = "/itop")
	public String ITopControllerGet(Map<String, Object> model) {
		
		final String uri =  "http://itop-32c300.appfleet.net/webservices/rest.php?version=1.3&json_data={ \"operation\": \"core/get\", \"class\": \"Person\", \"key\": \"SELECT Person WHERE email LIKE '%'\", \"output_fields\": \"friendlyname, email,phone\" }";
		RestTemplate rest = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());  

        ResponseEntity<String> response = rest.exchange(uri, HttpMethod.GET,request, String.class);  
        List<Contact> list = new ArrayList<Contact>();
        String [] splitsJSON = response.getBody().replace("{\"objects\":{", "").split("}},");
        
        for(int i = 0, j=2; i<splitsJSON.length-2;i++,j++) {
        	splitsJSON[i] = splitsJSON[i].replace("\"Person::"+ j+"\":{\"code\":0,\"message\":\"\",\"class\":\"Person\",\"key\":\""+j+"\",\"fields\":{"
        +"\"friendlyname\":", "").replace("\"email\":", "").replace("\"phone\":", "").replaceAll("\"", "");
        	
        	String [] splitsContact = splitsJSON[i].split(",");
            Contact c = new Contact(splitsContact[0], splitsContact[1], splitsContact[2]);
            list.add(c);
        }
        model.put("contacts", list);
		return "itop";
	}

}
