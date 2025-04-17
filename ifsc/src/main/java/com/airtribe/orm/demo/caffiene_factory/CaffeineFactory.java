package com.airtribe.orm.demo.caffiene_factory;


import com.airtribe.orm.demo.config.CaffeineConfig;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineStatsCounter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.NonNull;

@Builder
@Data
@AllArgsConstructor
public class CaffeineFactory<T, R> {

  public @NonNull LoadingCache<T, R> buildCache(
      CaffeineConfig<T, R> caffeineConfig,
      CacheEmptyAction<T, R> cacheEmptyAction,
      MeterRegistry meterRegistry) {
    Caffeine<Object, Object> caffeineCacheBuilder = Caffeine.newBuilder();
    caffeineCacheBuilder.expireAfter(caffeineConfig);
    caffeineCacheBuilder.maximumSize(caffeineConfig.maxSize());
    if(meterRegistry != null) {
      caffeineCacheBuilder.recordStats(() -> new CaffeineStatsCounter(meterRegistry, caffeineConfig.getCacheName()));
    } else {
      caffeineCacheBuilder.recordStats();
    }
    return caffeineCacheBuilder.build(cacheEmptyAction::onEmptyCache);
  }
}
