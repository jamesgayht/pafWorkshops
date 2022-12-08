package vttp.paf.day29.repositories;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.paf.day29.AppConfig;
import vttp.paf.day29.models.MarvelCharacter;

@Repository
public class MarvelCache {

    
    
    @Autowired @Qualifier(AppConfig.CACHE_MARVEL)
    private RedisTemplate<String, String> redisTemplate; 

    // putting data into redis cache
    public void cache(String key, List<MarvelCharacter> values) {

        ValueOperations<String, String> ops = redisTemplate.opsForValue(); 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        values.stream().forEach(c -> {
                arrayBuilder.add(c.toJson()); 
            });

        ops.set(key, arrayBuilder.build().toString());

    }


    // getting data from redis cache
    public Optional<List<MarvelCharacter>> get(String name) {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(name); 
        if(null == value) {
            return Optional.empty(); 
        }

        JsonReader reader = Json.createReader(new StringReader(value)); 
        JsonArray results = reader.readArray(); 

        List<MarvelCharacter> characters = results.stream()
                                            .map(v -> (JsonObject)v)
                                            .map(v -> MarvelCharacter.fromCache(v))
                                            .toList();

        return Optional.of(characters); 
    }

}
