package com.airtribe.orm.demo.caffiene_factory;

import com.airtribe.orm.demo.config.CaffeineConfig;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

public interface ICacheConnector<T, R> {

  R get(T t);

  R getIfPresent(T t);

  void put(T t, R r);

  long getEstimatedSize();

  void invalidate(T t);

  void invalidateAll();

  CaffeineConfig<T, R> initCaffeineConfig();

  LoadingCache<T, R> getLoadingCaffeineCache();

  CacheEmptyAction<T,R> createCacheEmptyAction();

  CacheStats getCacheStats();

}
