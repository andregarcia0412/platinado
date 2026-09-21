package me.andregarcia0412.pipeline.shared.provider.cache;

import me.andregarcia0412.pipeline.shared.provider.cache.exception.CacheSerializationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Optional;

@Component
public class RedisCacheProvider implements CacheProviderPort {
    private final StringRedisTemplate redis;
    private final ObjectMapper mapper;

    public RedisCacheProvider(StringRedisTemplate redis, ObjectMapper mapper) {
        this.redis = redis;
        this.mapper = mapper;
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        String raw = redis.opsForValue().get(key);
        if(raw == null) return Optional.empty();

        try {
            return Optional.of(mapper.readValue(raw, type));
        } catch (JacksonException exception) {
            redis.delete(key);
            return Optional.empty();
        }
    }

    @Override
    public <T> void set(String key, T value, Duration ttl) {
        try {
            redis.opsForValue().set(key, mapper.writeValueAsString(value), ttl);
        } catch (JacksonException exception) {
            throw new CacheSerializationException(key, exception);
        }
    }

    @Override
    public void delete(String key) {
        redis.delete(key);
    }
}