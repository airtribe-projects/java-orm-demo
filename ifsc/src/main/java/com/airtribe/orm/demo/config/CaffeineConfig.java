package com.airtribe.orm.demo.config;

import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.Ticker;
import lombok.Data;
import lombok.Getter;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.concurrent.TimeUnit;

@Data
public class CaffeineConfig<K, V> implements Expiry<K, V> {
    private static final long DEFAULT_EXPIRY_DURATION = 3600;
    private static final int DEFAULT_MAX_SIZE = 3000;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    private int maxCacheSize;
    private TimeUnit timeUnit;
    /**
     * -- GETTER --
     * Specifies the name of the cache.
     *
     * @return cacheName
     */
    @Getter
    private String cacheName;


    public CaffeineConfig(int maxCacheSize, TimeUnit timeUnit, String cacheName) {
        this.maxCacheSize = maxCacheSize == 0 ? DEFAULT_MAX_SIZE : maxCacheSize;
        this.cacheName = cacheName;
        this.timeUnit = timeUnit == null ? DEFAULT_TIME_UNIT : timeUnit;
    }


    /**
     * Specifies the maximum number of entries the cache may contain. Note that the cache may evict an
     * entry before this limit is exceeded. As the cache size grows close to the maximum, the cache
     * evicts entries that are less likely to be used again.
     *
     * @return maxSize
     */
    public long maxSize() {
        return this.maxCacheSize;
    }

    @Override
    public long expireAfterCreate(K key, V value, long currentTime) {
        return this.timeUnit.toMillis(DEFAULT_EXPIRY_DURATION);
    }

    /**
     * Specifies that the entry should be automatically removed from the cache once the duration has
     * elapsed after the replacement of its value. To indicate no expiration an entry may be given an
     * excessively long period, such as {@code Long#MAX_VALUE}. The {@code currentDuration} may be
     * returned to not modify the expiration time.
     * <p>
     * <b>Note:</b> The {@code currentTime} is supplied by the configured {@link Ticker} and by
     * default does not relate to system or wall-clock time. When calculating the duration based on a
     * time stamp, the current time should be obtained independently.
     *
     * @param key             the key represented by this entry
     * @param value           the value represented by this entry
     * @param currentTime     the current time, in nanoseconds
     * @param currentDuration the current duration, in nanoseconds
     * @return the length of time before the entry expires, in nanoseconds
     */
    @Override
    public long expireAfterUpdate(K key, V value, long currentTime, @NonNegative long currentDuration) {
        return currentDuration;
    }

    /**
     * Specifies that the entry should be automatically removed from the cache once the duration has
     * elapsed after its last read. To indicate no expiration an entry may be given an excessively
     * long period, such as {@code Long#MAX_VALUE}. The {@code currentDuration} may be returned to not
     * modify the expiration time.
     * <p>
     * <b>Note:</b> The {@code currentTime} is supplied by the configured {@link Ticker} and by
     * default does not relate to system or wall-clock time. When calculating the duration based on a
     * time stamp, the current time should be obtained independently.
     *
     * @param key             the key represented by this entry
     * @param value           the value represented by this entry
     * @param currentTime     the current time, in nanoseconds
     * @param currentDuration the current duration, in nanoseconds
     * @return the length of time before the entry expires, in nanoseconds
     */
    @Override
    public long expireAfterRead(K key, V value, long currentTime, @NonNegative long currentDuration) {
        return currentDuration;
    }
}
