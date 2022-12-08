package vttp.paf.day29;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {
    
    public static final String CACHE_MARVEL = "marvel-cache"; 

    @Value("${spring.redis.host}")
    private String redisHost; 

    @Value("${spring.redis.port}")
    private Integer port; 

    @Value("${spring.redis.user}")
    private String user; 

    @Value("${spring.redis.password}")
    private String password; 

    @Value("${spring.redis.database}")
    private Integer database; 

    // for redis template can refer to day14 notes 
    @Bean(CACHE_MARVEL)
    public RedisTemplate<String, String> createRedisTemplate() {

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(); 
        config.setHostName(redisHost);
        config.setPort(port);
        config.setUsername(user);
        config.setPassword(password);
        config.setDatabase(database);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient); 
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, String> template = new RedisTemplate<>(); 
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template; 
    }
}
