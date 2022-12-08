package vttp.paf.day29.services;

import java.io.StringReader;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.paf.day29.models.MarvelCharacter;

@Service
public class MarvelService {

    // Inject into the service Marvel's private and public key
    @Value("${MARVEL_PUBLIC_KEY}")
    private String publicKey;

    @Value("${MARVEL_PRIVATE_KEY}")
    private String privateKey;

    public static final String URL = "https://gateway.marvel.com:443/v1/public/characters?";

    public List<MarvelCharacter> search (String name) {
        return search(name, 10); 
    }

    public List<MarvelCharacter> search (String name, Integer limit) {

        // https://gateway.marvel.com:443/v1/public/characters?
        // nameStartsWith=silver
        // limit=10
        // ts=
        // apikey=
        // hash=

        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = ""; 

        try {
            // get an instance of md5
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // calculate our hash
            // update our message digest
            md5.update(signature.getBytes());

            // get the md5 digest
            byte[] h =md5.digest();

            // stringify the MD5 digest
            hash = HexFormat.of().formatHex(h); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        String requestUrl = UriComponentsBuilder.fromUriString(URL)
                .queryParam("nameStartsWith", name)
                .queryParam("limit", limit)
                .queryParam("ts", ts)
                .queryParam("apikey", publicKey)
                .queryParam("hash", hash)
                .toUriString();

        System.out.println("URL >>>>> " + requestUrl);

        // use the url to make a call to marvel
        RequestEntity<Void> req = RequestEntity.get(requestUrl).accept(MediaType.APPLICATION_JSON).build(); 

        RestTemplate restTemplate = new RestTemplate(); 
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class); 
        String payload = resp.getBody(); 

/*
     * {
     * "code": 200,
     * "status": "Ok",
     * "copyright": "© 2022 MARVEL",
     * "attributionText": "Data provided by Marvel. © 2022 MARVEL",
     * "attributionHTML":
     * "<a href=\"http://marvel.com\">Data provided by Marvel. © 2022 MARVEL</a>",
     * "etag": "54740628ae0f91e059c672490fd60d3dcd627d38",
     * "data": {
         "offset": 0,
         "limit": 10,
         "total": 9,
         "count": 9,
         "results": [
             {
             "id": 1009281,
             "name": "Doctor Doom",
             "description": "",
             "modified": "2016-06-22T12:07:32-0400",
             "thumbnail": {
             "path": "http://i.annihil.us/u/prod/marvel/i/mg/3/60/53176bb096d17",
             "extension": "jpg"
             }
     */

        // Parse the String to JsonObject
        JsonReader jsonReader = Json.createReader(new StringReader(payload)); 
        JsonObject results = jsonReader.readObject(); 
        JsonArray data = results.getJsonObject("data").getJsonArray("results"); 

        // retrieve the name description and image 
        List<MarvelCharacter> characters = new LinkedList<>(); 
        for(Integer i = 0; i<data.size(); i++) {
            characters.add(MarvelCharacter.createJson(data.getJsonObject(i))); 
        }
        System.out.println(characters.toString());
        return characters; 
    }

}
