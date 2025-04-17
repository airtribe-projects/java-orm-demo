package com.airtribe.orm.demo.repositories.redis;

import com.airtribe.orm.demo.distributed_cache.factory.ICacheManager;
import com.airtribe.orm.demo.entities.redis.CachedProductEntity;
import com.airtribe.orm.demo.daos.BaseCacheRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRedisRepository extends BaseCacheRepository<CachedProductEntity> {
    /**
     * Constructor for BaseCacheRepository.
     *
     * @param cacheManager CacheManager instance to be used for cache operations.
     */
    private static final String CACHE_NAME_SPACE = "aod_product";

    protected UserRedisRepository(ICacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public String getCacheNamespace() {
        return CACHE_NAME_SPACE;
    }

    @Override
    public long getCacheVersion() {
        return 1;
    }

    public CachedProductEntity get(String key) {
        return super.get(key, CachedProductEntity.class);
    }

    public CachedProductEntity save(String key, CachedProductEntity value) {
        super.save(key, value, getDEFAULT_TTL_TIME(), getDEFAULT_TTL_TIME_UINT());
        return value;
    }

    public CachedProductEntity saveWithoutTTl(String key, CachedProductEntity value) {
        super.saveWithoutTTl(key, value);
        return value;
    }

    public Boolean remove(String key) {
        return super.remove(key);
    }

    public Boolean hasKey(String key) {
        return super.hasKey(key);
    }
}
