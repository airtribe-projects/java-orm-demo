package com.airtribe.orm.demo.caffiene_factory;

public interface CacheEmptyAction<T,R> {
  R onEmptyCache(T key);
}
