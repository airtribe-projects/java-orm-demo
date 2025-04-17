package com.airtribe.orm.demo.distributed_cache.manager;
import com.airtribe.orm.demo.Exceptions.CacheException;
import com.airtribe.orm.demo.distributed_cache.enums.Provider;
import com.airtribe.orm.demo.distributed_cache.factory.ICacheManager;
import com.airtribe.orm.demo.distributed_cache.factory.impl.CacheStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnExpression(value = "${cache.manager.enabled:false}")
public class CacheManager implements ICacheManager {

    private final Provider provider;

    private final CacheStrategyFactory cacheStrategyFactory;

    @Autowired
    public CacheManager(
            @Value("${cache.provider}") Provider provider,
            CacheStrategyFactory cacheStrategyFactory
    ) {
        this.provider = provider;
        this.cacheStrategyFactory = cacheStrategyFactory;
    }

    @Override
    public Boolean hasKey(String key) throws CacheException {
        return cacheStrategyFactory.findStrategy(provider).hasKey(key);
    }

    @Override
    public void saveWithoutTTl(String key, Object value) throws CacheException {
        cacheStrategyFactory.findStrategy(provider).saveWithoutTTl(key, value);
    }

    @Override
    public void save(String key, Object value, int ttl, TimeUnit timeUnit) throws CacheException {
        cacheStrategyFactory.findStrategy(provider).save(key, value, ttl, timeUnit);
    }

    @Override
    public Boolean remove(String key) throws CacheException {
        return cacheStrategyFactory.findStrategy(provider).remove(key);
    }

    @Override
    public <T> T get(String key, Class<T> klass) throws CacheException {
        return cacheStrategyFactory.findStrategy(provider).get(key, klass);
    }

}
