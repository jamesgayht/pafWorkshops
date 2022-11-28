package vttp.paf.day25.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/purchase", produces = "text/html")
public class PurchaseController {
    
    @PostMapping
    public String postPurchase(@RequestBody MultiValueMap<String, String> form, Model model) {

        // retrieve the data from the form 
        String name = form.getFirst("name");
        Integer numTickets = Integer.parseInt(form.getFirst("numTickets"));
        String ticketOrder = UUID.randomUUID().toString().substring(0, 8);

        System.out.printf("Order Ticker: %s\n Issuing %d tickets for %s\n", name, numTickets, ticketOrder);

        model.addAttribute("name", name);
        model.addAttribute("ticketOrder", ticketOrder);
        model.addAttribute("numTickets", numTickets);

        return "purchases";
    }
}
