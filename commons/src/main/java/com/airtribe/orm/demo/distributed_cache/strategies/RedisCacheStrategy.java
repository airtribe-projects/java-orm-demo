package com.airtribe.orm.demo.distributed_cache.strategies;

import com.airtribe.orm.demo.Exceptions.CacheException;
import com.airtribe.orm.demo.distributed_cache.enums.Provider;
import com.airtribe.orm.demo.distributed_cache.factory.CacheStrategy;
import com.airtribe.orm.demo.distributed_cache.data_store.RedisDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnExpression(value = "${cache.manager.enabled:false}")
public class RedisCacheStrategy implements CacheStrategy {

    @Autowired
    private final RedisDataStore redisDataStore;

    public RedisCacheStrategy(RedisDataStore redisDataStore) {
        this.redisDataStore = redisDataStore;
    }

    @Override
    public Boolean hasKey(String key) throws CacheException {
        try {
            return redisDataStore.hasKey(key);
        } catch (Exception e) {
            throw CacheException.builder()
                    .errorCode("CACHE_HAS_KEY_ERROR")
                    .errorMessage("Error checking if key exists in cache")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void saveWithoutTTl(String key, Object value) throws CacheException {
        try {
            redisDataStore.saveWithoutTTl(key, value);
        } catch (Exception e) {
            throw CacheException.builder()
                    .errorCode("CACHE_SAVE_ERROR")
                    .errorMessage("Error saving data to cache")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public void save(String key, Object value, int ttl, TimeUnit timeUnit) throws CacheException {
        try {
            redisDataStore.save(key, value, ttl, timeUnit);
        } catch (Exception e) {
            throw CacheException.builder()
                    .errorCode("CACHE_SAVE_ERROR")
                    .errorMessage("Error saving data to cache")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public Boolean remove(String key) throws CacheException {
        try {
            return redisDataStore.remove(key);
        } catch (Exception e) {
            throw CacheException.builder()
                    .errorCode("CACHE_REMOVE_ERROR")
                    .errorMessage("Error removing data from cache")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public <T> T get(String key, Class<T> klass) throws CacheException {
        try {
            return redisDataStore.get(key, klass);
        } catch (Exception e) {
            throw CacheException.builder()
                    .errorCode("CACHE_GET_ERROR")
                    .errorMessage("Error getting data from cache")
                    .cause(e)
                    .build();
        }
    }

    @Override
    public Provider getStrategyType() {
        return Provider.REDIS;
    }
}
