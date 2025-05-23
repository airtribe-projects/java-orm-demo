package com.airtribe.orm.demo.distributed_cache.factory;

import com.airtribe.orm.demo.Exceptions.CacheException;
import com.airtribe.orm.demo.distributed_cache.enums.Provider;

import java.util.concurrent.TimeUnit;

public interface CacheStrategy {

    Boolean hasKey(String key) throws CacheException;

    void saveWithoutTTl(String key, Object value) throws CacheException;

    void save(String key, Object value, int ttl, TimeUnit timeUnit) throws CacheException;

    <T> T get(String key, Class<T> klass) throws CacheException;

    Boolean remove(String key) throws CacheException;

    Provider getStrategyType();


}
