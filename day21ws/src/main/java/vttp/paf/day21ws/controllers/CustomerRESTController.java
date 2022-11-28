package vttp.paf.day21ws.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp.paf.day21ws.models.Customer;
import vttp.paf.day21ws.models.Order;
import vttp.paf.day21ws.repositories.CustomerRepo;
import vttp.paf.day21ws.repositories.OrderRepo;

@RestController
@RequestMapping(path="/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)

public class CustomerRESTController {

    @Autowired
    private CustomerRepo customerRepo; 

    @Autowired
    private OrderRepo orderRepo; 

    @GetMapping(path = "{limit}/{offset}")
    public ResponseEntity<String> getLimitOffset(@PathVariable Integer limit, @PathVariable Integer offset) {

        // query the database for customers 
        List<Customer> customers = customerRepo.getCustomers(limit, offset); 

        // build the result 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        for(Customer c:customers) {
            arrayBuilder.add(c.toJSON()); 
        }

        JsonArray result = arrayBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString()); 
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> getLimitOffset() {

        // query the database for customers 
        List<Customer> customers = customerRepo.getCustomers(); 

        // build the result 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        for(Customer c:customers) {
            arrayBuilder.add(c.toJSON()); 
        }

        JsonArray result = arrayBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString()); 
    }

    // @GetMapping(path = "{limit}")
    // public ResponseEntity<String> getLimitOffset(@PathVariable Integer limit) {

    //     // query the database for customers 
    //     List<Customer> customers = customerRepo.getCustomers(limit); 

    //     // build the result 
    //     JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
    //     for(Customer c:customers) {
    //         arrayBuilder.add(c.toJSON()); 
    //     }

    //     JsonArray result = arrayBuilder.build();

    //     return ResponseEntity
    //             .status(HttpStatus.OK)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .body(result.toString()); 
    // }

    @GetMapping(path = "{customerId}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer customerId) {
        
        // query the database for customers
        List<Customer> customers = customerRepo.getCustomerById(customerId);

        // build the result
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        for(Customer c:customers) {
            arrayBuilder.add(c.toJSON()); 
        }

        JsonArray result = arrayBuilder.build(); 

        if(result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("This customer does not exist");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @GetMapping(path = "{customerId}/orders")
    public ResponseEntity<String> getOrdersById(@PathVariable Integer customerId) {
        
        // query the database for customers 
        List<Order> orders = orderRepo.getOrders(customerId);

        // build the result 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        for(Order o:orders) {
            arrayBuilder.add(o.toJson()); 
        }

        JsonArray result = arrayBuilder.build(); 

        if(result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("This customers does not exist, try again");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString()); 
        }
    }
    
}
