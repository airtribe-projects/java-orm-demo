package com.airtribe.orm.demo.distributed_cache.data_store;

import com.airtribe.orm.demo.Exceptions.RedisException;
import com.airtribe.orm.demo.utils.RedisValidationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@ConditionalOnExpression(value = "${cache.manager.enabled:false}")
public class RedisDataStore {
    private final ValueOperations<String, Object> redisValueOperations;

    private final HashOperations<String, String, Object> redisHashOperations;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public RedisDataStore(
            RedisTemplate<String, Object> redisTemplate,
            ObjectMapper objectMapper
    ) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.redisValueOperations = redisTemplate.opsForValue();
        this.redisHashOperations = redisTemplate.opsForHash();
    }

    /**
     * @param key ---> name of the map
     * @return a Map of String and Object
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method
     *                        <p>
     *                        get Map from Redis which is being stored in redis with respect to the
     *                        key
     */
    public Map<String, Object> getMap(String key) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            return redisHashOperations.entries(key);
        } catch (Exception ex) {
            log.error("Error RedisDataStore.getMap: {} {}", key, ex.getMessage());
            throw new RedisException(ex);
        }
    }

    /**
     * @param key     ---> name of the map
     * @param entries ---> hashKey and their respective value in the form of Map
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method create a map in redis
     *                        with respect to the key. The key of entries will be used as HashKey and
     *                        Objects will be used as their values.
     */
    public void setMapWithoutTTl(String key, Map<String, Object> entries) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            redisHashOperations.putAll(key, entries);
        } catch (Exception ex) {
            log.error("Error RedisDataStore.setMap: {} {}", key, ex.getMessage());
            throw new RedisException(ex);
        }

    }

    /**
     * @param key      ----> name of the map
     * @param entries  ---> hashKey and their respective value in the form of Map
     * @param ttl      ----> Time to live
     * @param timeUnit ----> time unit of ttl
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method create a map in redis
     *                        with respect to the key , with defined TTL and TimeUnit The key of
     *                        entries will be used as HashKey and Objects will be used as their
     *                        values.
     */
    public void setMap(String key, Map<String, Object> entries, int ttl, TimeUnit timeUnit)
            throws RedisException {
        try {
            setMapWithoutTTl(key, entries);
            setExpiry(key, ttl, timeUnit);
        } catch (Exception ex) {
            log.error("Error RedisDataStore.setMapWithTTL: {} {}", key, ex.getMessage());
            throw new RedisException(ex);
        }

    }

    /**
     * @param key ----> name of the map
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method clear the map with
     *                        respect to the key
     */
    public void clearMap(String key) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            Map<String, Object> map = getMap(key);
            map.keySet().forEach(hashKey -> redisHashOperations.delete(key, hashKey));

        } catch (Exception ex) {
            log.error("Error RedisDataStore.clearMap: {} {}", key, ex.getMessage());
            throw new RedisException(ex);
        }

    }

    /**
     * @param key ----> name of the key by which a value is being stored in redis
     * @return TRUE / FALSE
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method checks if key exists
     *                        in Redis or not
     */
    public Boolean hasKey(String key) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            return redisTemplate.hasKey(key);
        } catch (Exception ex) {
            log.error("Error RedisRepositoryUtil.isEmptyMap: {} {}", key, ex);
        }
        return Boolean.FALSE;
    }

    /**
     * @param key      ----> name of the key by which a value is being stored in redis
     * @param ttl      ----> Time to Live
     * @param timeUnit ----> TImeUnit of TTL set expire on any key in Redis
     */
    private void setExpiry(String key, int ttl, TimeUnit timeUnit) {
        redisTemplate.expire(key, ttl, timeUnit);
    }

    /**
     * @param key   ----> name of the key by which a value is being stored in redis
     * @param value ---> value of the key which will be stored in Redis
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method save any Object in
     *                        Redis with respect to a key, the Object will be converted into String
     *                        internally.
     */
    public void saveWithoutTTl(String key, Object value) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            String data = objectMapper.writeValueAsString(value);
            redisValueOperations.set(key, data);
        } catch (Exception e) {
            throw new RedisException(e);
        }
    }

    /**
     * @param key      ----> name of the key by which a value is being stored in redis
     * @param value    ---> value of the key which will be stored in Redis
     * @param ttl      ----> Time to Live
     * @param timeUnit ---> Time Unit of Time to Live
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method save any Object in
     *                        Redis with respect to a key with a defined TTL, the Object will be
     *                        converted into String internally.
     */
    public void save(String key, Object value, int ttl, TimeUnit timeUnit) throws RedisException {
        try {
            saveWithoutTTl(key, value);
            setExpiry(key, ttl, timeUnit);
        } catch (Exception e) {
            throw new RedisException(e);
        }
    }

    /**
     * @param key   ----> name of the key by which a value is being stored in redis
     * @param klass ---> expected type of the object which will be retrieved from Redis
     * @param <T>   ----> klass type of Object
     * @return ----> Object of type klass
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method get Value from a
     *                        Redis, only Value Operations (non collection objects)
     */
    public <T> T get(String key, Class<T> klass) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            String value = (String) redisValueOperations.get(key);

            if (Objects.nonNull(value)) {
                return objectMapper.readValue(value, klass);
            }
            return null;
        } catch (Exception ex) {
            log.error("Error RedisRepositoryUtil.get: {} {}", key, ex);
            throw new RedisException(ex);
        }
    }

    /**
     * @param key ----> name of the key by which a value is being stored in redis
     * @return TRUE / FALSE
     * @throws RedisException Any exception occurred during the operation will be converted into
     *                        RedisException and will be thrown from the method remove a key from
     *                        redis
     */
    public Boolean remove(String key) throws RedisException {
        try {
            RedisValidationUtils.validateKey(key);
            return redisTemplate.delete(key);
        } catch (Exception ex) {
            log.error("Error Occurred RedisRepositoryUtil.remove: {} {}", key, ex.getMessage());
            throw new RedisException(ex);
        }
    }
}
