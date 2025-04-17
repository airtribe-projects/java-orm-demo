package com.airtribe.orm.demo.daos;

import java.util.List;

public interface BaseDao<T> {

    T save(T entity);

    T saveAndFlush(T entity);

    List<T> saveAll(List<T> entities);

    List<T> saveAllAndFlush(List<T> entities);

    void populateCache(T entity);

    void evictCache(T entity);
}
