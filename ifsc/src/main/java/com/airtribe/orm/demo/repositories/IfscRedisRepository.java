package com.airtribe.orm.demo.repositories;
import com.airtribe.orm.demo.distributed_cache.factory.ICacheManager;
import com.airtribe.orm.demo.models.Response.CachedIfscDetails;
import com.airtribe.orm.demo.daos.BaseCacheRepository;
import org.springframework.stereotype.Component;

@Component
public class IfscRedisRepository extends BaseCacheRepository<CachedIfscDetails> {

    /**
     * Constructor for BaseCacheRepository.
     *
     * @param cacheManager CacheManager instance to be used for cache operations.
     */
    protected IfscRedisRepository(ICacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public String getCacheNamespace() {
        return "IFSC";
    }

    @Override
    public long getCacheVersion() {
        return 1;
    }

    public CachedIfscDetails get(String ifsc) {
        return super.get(ifsc, CachedIfscDetails.class);
    }

    public void save(String ifsc, CachedIfscDetails value) {
        super.save(ifsc, value, getDEFAULT_TTL_TIME(), getDEFAULT_TTL_TIME_UINT());
    }

    public void saveWithNoTTl(String ifsc, CachedIfscDetails value) {
        super.saveWithoutTTl(ifsc, value);
    }

}
