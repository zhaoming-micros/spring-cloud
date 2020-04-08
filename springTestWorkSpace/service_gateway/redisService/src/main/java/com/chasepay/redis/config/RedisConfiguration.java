package com.chasepay.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfiguration {
	
	//private static Logger logger = LogManager.getLogger(RedisConfiguration.class);
	
	//@Resource
	//private LettuceConnectionFactory lettuceConnectionFactory;

	//private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	
	
	private RedisSerializer<String> keySerializer =  new StringRedisSerializer();
	
	private RedisSerializer<Object> valueSerializer =  new GenericJackson2JsonRedisSerializer();
	
	/*@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory)
	{
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
		.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
		.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
		.disableCachingNullValues();

		
		Map<String,RedisCacheConfiguration> cacheConfigMap=new HashMap<>();
		cacheConfigMap.put("cache1",redisCacheConfiguration.entryTtl(Duration.ofHours(24)));
		cacheConfigMap.put("cache2",redisCacheConfiguration.entryTtl(Duration.ofHours(30)));
		
		RedisCacheManager build = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration)
		.transactionAware().withInitialCacheConfigurations(cacheConfigMap).build();
	    
	    return build;
	}
	
	@Bean
	public KeyGenerator generateCacheKey()
	{
		return (target, method, params) -> 
		{
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object param : params) {
                sb.append(param.toString());
            }
            return sb.toString();
        };
	}*/
	
	@Bean
	public RedisTemplate<String, Object> chasepayRedisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
		
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		
		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		
		return redisTemplate;
		
	}
}
