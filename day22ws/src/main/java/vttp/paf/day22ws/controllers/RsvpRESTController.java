package vttp.paf.day22ws.controllers;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp.paf.day22ws.models.Rsvp;
import vttp.paf.day22ws.repositories.RsvpRepo;
import vttp.paf.day22ws.services.RsvpService;

@RestController
@RequestMapping(path = "/api/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
public class RsvpRESTController {

    @Autowired
    private RsvpRepo rsvpRepo;

    @Autowired
    private RsvpService rsvpService;

    @GetMapping(path = "")
    public ResponseEntity<String> getAllRsvps() {

        // query the databse for rsvps
        List<Rsvp> rsvps = rsvpRepo.getRsvps();

        // build the result
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Rsvp r : rsvps)
            arrayBuilder.add(r.toJSON());

        JsonArray result = arrayBuilder.build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> getRsvpsByName(@RequestParam("q") String name) {

        // querey the database for rsvp
        List<Rsvp> rsvps = rsvpRepo.getRsvpByName(name);

        // build the results
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Rsvp r : rsvps)
            arrayBuilder.add(r.toJSON());

        JsonArray result = arrayBuilder.build();

        if (result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("could not find any RSVPs for " + name);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createNewRsvp(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("confirmationDate") String confirmationDate, @RequestParam("comments") String comments) throws ParseException {

        System.out.printf("name: %s\nemail: %s\nphone: %s\n", name, email, phone);

        Rsvp rsvp = new Rsvp();
        rsvp.setName(name);
        rsvp.setEmail(email);
        rsvp.setPhone(phone);
        rsvp.setConfirmationDate(Date.valueOf(confirmationDate));
        rsvp.setComments(comments);

        try {
            if (!rsvpService.createRsvp(rsvp))
                System.out.println(">>>>> ERROR! RSVP not created <<<<<");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.toString());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(name + " RSVP created successfully");
    }

    @PutMapping("{email}")
    public ResponseEntity<String> updateRsvp(@PathVariable String email) {

        Rsvp rsvp = new Rsvp();

        return null;
    }

}
