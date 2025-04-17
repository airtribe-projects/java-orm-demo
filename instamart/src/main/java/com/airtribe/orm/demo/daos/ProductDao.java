package com.airtribe.orm.demo.daos;

import com.airtribe.orm.demo.Exceptions.UnsupportedException;
import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.entities.redis.CachedProductEntity;
import com.airtribe.orm.demo.repositories.mySql.ProductMySqlRepository;
import com.airtribe.orm.demo.repositories.redis.ProductRedisRepository;
import com.airtribe.orm.demo.utils.ProductMappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProductDao implements BaseDao<StoredProductEntity> {

    private final ProductMySqlRepository productMySqlRepository;
    private final ProductRedisRepository productRedisRepository;
    private final ProductMappers productMappers;

    public ProductDao(ProductMySqlRepository productMySqlRepository, ProductRedisRepository productRedisRepository, ProductMappers productMappers) {
        this.productMySqlRepository = productMySqlRepository;
        this.productRedisRepository = productRedisRepository;
        this.productMappers = productMappers;
    }

    @Override
    public StoredProductEntity save(StoredProductEntity entity) {
        evictCache(entity);
        return productMySqlRepository.save(entity);
    }

    @Override
    public StoredProductEntity saveAndFlush(StoredProductEntity entity) {
        evictCache(entity);
        return productMySqlRepository.saveAndFlush(entity);
    }

    @Override
    public List<StoredProductEntity> saveAll(List<StoredProductEntity> entities) {
        entities.forEach(this::evictCache);
        return productMySqlRepository.saveAll(entities);
    }

    @Override
    public List<StoredProductEntity> saveAllAndFlush(List<StoredProductEntity> entities) {
        entities.forEach(this::evictCache);
        return productMySqlRepository.saveAllAndFlush(entities);
    }

    @Override
    public void populateCache(StoredProductEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Populate Cache Operation is not supported in Product Dao");
    }

    @Override
    public void evictCache(StoredProductEntity entity) {
        productRedisRepository.remove(entity.getProductId());
    }

    public StoredProductEntity findByProductId(String productId) {
        return productMySqlRepository.findByProductId(productId);
    }

    public CachedProductEntity getProductDetails(String productId) {
        CachedProductEntity cachedProductEntity = productRedisRepository.get(productId);
        if (Objects.isNull(cachedProductEntity)) {
            StoredProductEntity storedProductEntity = findByProductId(productId);
            if (Objects.isNull(storedProductEntity)) {
                return null;
            }
            cachedProductEntity = productRedisRepository.save(productId, productMappers.fromMySqlToRedisEntity(storedProductEntity));
        }
        return cachedProductEntity;
    }
}
