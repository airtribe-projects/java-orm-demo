package com.airtribe.orm.demo.daos;

import com.airtribe.orm.demo.Exceptions.CacheException;
import com.airtribe.orm.demo.distributed_cache.factory.ICacheManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Base class for all Cache repositories to be used in the application.
 *
 * @param <T> Type of the object to be stored in redis.
 */
@Slf4j
public abstract class BaseCacheRepository<T> {

    private static final String DELIMITER = ".";

    private final ICacheManager cacheManager;

    @Getter
    public TimeUnit DEFAULT_TTL_TIME_UINT = TimeUnit.SECONDS;
    @Getter
    public int DEFAULT_TTL_TIME = 60 * 60;


    /**
     * Constructor for BaseCacheRepository.
     *
     * @param cacheManager CacheManager instance to be used for cache operations.
     */
    protected BaseCacheRepository(ICacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Get the namespace for the cache repository.
     * <p>
     * This is used to form the final key for the cache, track metrics and invalidate the cache.
     *
     * @return The namespace for the cache repository.
     */
    public abstract String getCacheNamespace();

    /**
     * Get the current version for the cache repository.
     * <p>
     * Used to form the final key for the cache, track metrics and invalidate the cache.
     *
     * @return The version for the cache repository.
     */
    public abstract long getCacheVersion();

    /**
     * Get the value for the given key from the cache.
     *
     * @param key   The key for the cache.
     * @param klass The class of the object to be returned.
     * @return The value for the given key from the cache.
     * @throws CacheException If there is an error while fetching the value from the cache.
     */
    public T get(String key, Class<T> klass) throws CacheException {
        try {
            final T result = cacheManager.get(this.formCacheAccessKey(key), klass);
            log.info("Cache find result: {}", result);
            if (Objects.nonNull(result)) {
                log.info("Cache Hit: {}", key);
            } else {
                log.info("Cache Miss: {}", key);
            }
            return result;
        } catch (CacheException e) {
            log.error("Cache get Exception: ", e);
            throw e;
        }
    }

    /**
     * Save the given value in the cache with the given ttl.
     *
     * @param key      The key for the cache.
     * @param value    The value to be saved in the cache.
     * @param ttl      The ttl for the cache.
     * @param timeUnit The time unit for the ttl.
     * @throws CacheException If there is an error while saving the value in the cache.
     */
    public T save(String key, T value, Integer ttl, TimeUnit timeUnit) throws CacheException {
        ttl = Objects.nonNull(ttl) ? ttl : this.DEFAULT_TTL_TIME;
        timeUnit = Objects.nonNull(timeUnit) ? timeUnit : this.DEFAULT_TTL_TIME_UINT;
        cacheManager.save(this.formCacheAccessKey(key), value, ttl, timeUnit);
        return value;
    }

    /**
     * Save the given value in the cache without any ttl.
     *
     * @param key   The key for the cache.
     * @param value The value to be saved in the cache.
     * @throws CacheException If there is an error while saving the value in the cache.
     */
    public T saveWithoutTTl(String key, T value) throws CacheException {
        cacheManager.saveWithoutTTl(this.formCacheAccessKey(key), value);
        return value;
    }

    /**
     * Remove the value for the given key from the cache.
     *
     * @param key The key for the cache.
     * @return True if the value was removed from the cache, false otherwise.
     * @throws CacheException If there is an error while removing the value from the cache.
     */
    public Boolean remove(String key) throws CacheException {
        return cacheManager.remove(this.formCacheAccessKey(key));
    }

    /**
     * Check if the given key is present in the cache.
     *
     * @param key The key for the cache.
     * @return True if the key is present in the cache, false otherwise.
     * @throws CacheException If there is an error while checking the presence of the key in the
     *                        cache.
     */
    public Boolean hasKey(String key) throws CacheException {
        return cacheManager.hasKey(this.formCacheAccessKey(key));
    }

    /**
     * Join the given key parts using the delimiter.
     *
     * @param args The key parts to be joined.
     * @return The joined key parts.
     */
    protected String joinKeyParts(String... args) {
        return String.join(DELIMITER, args);
    }

    /**
     * Form the final key for the cache, using the namespace, key and version.
     *
     * @param key The key for the cache.
     * @return The final key for the cache.
     */
    private String formCacheAccessKey(String key) {
        return this.joinKeyParts(this.getCacheNamespace(), key, this.getCacheVersionString());
    }

    /**
     * Gets the current version of the cache repository as a string.
     *
     * @return The current version of the cache repository as a string.
     */
    protected String getCacheVersionString() {
        return "v" + this.getCacheVersion();
    }
}
