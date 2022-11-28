package vttp.paf.ticketing_bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TicketingBotApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TicketingBotApplication.class, args);
	}

	@Override
	public void run(String ... args) {
		
		for(String a:args) {
			System.out.println(">>> args = " + a);
		}

		String name = args[2];
		String numTickets = args[3];
		
		MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
		form.add("name", name);
		form.add("numTickets", numTickets);

		// constrcut the request
		RequestEntity<MultiValueMap<String,String>> req = RequestEntity
			.post("http://localhost:8080/purchase")
			.accept(MediaType.TEXT_HTML)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(form);
		
		// create the resttemplate
		RestTemplate restTemplate = new RestTemplate();

		// make the request
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class); 

		String payload = resp.getBody();

		System.out.printf(">>>> purchase ticket response:\n%s\n", payload);
	}

}
