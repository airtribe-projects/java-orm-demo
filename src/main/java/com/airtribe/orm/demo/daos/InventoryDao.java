package com.airtribe.orm.demo.daos;

import com.airtribe.orm.demo.Exceptions.UnsupportedException;
import com.airtribe.orm.demo.daos.base.BaseDao;
import com.airtribe.orm.demo.entities.mysql.StoredInventoryEntity;
import com.airtribe.orm.demo.repositories.mySql.InventoryMySqlRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryDao implements BaseDao<StoredInventoryEntity> {
    private final InventoryMySqlRepository inventoryMySqlRepository;

    public InventoryDao(InventoryMySqlRepository inventoryMySqlRepository) {
        this.inventoryMySqlRepository = inventoryMySqlRepository;
    }

    @Override
    public StoredInventoryEntity save(StoredInventoryEntity entity) {
        return inventoryMySqlRepository.save(entity);
    }

    @Override
    public StoredInventoryEntity saveAndFlush(StoredInventoryEntity entity) {
        return inventoryMySqlRepository.saveAndFlush(entity);
    }

    @Override
    public List<StoredInventoryEntity> saveAll(List<StoredInventoryEntity> entities) {
        return inventoryMySqlRepository.saveAll(entities);
    }

    @Override
    public List<StoredInventoryEntity> saveAllAndFlush(List<StoredInventoryEntity> entities) {
        return inventoryMySqlRepository.saveAllAndFlush(entities);
    }

    @Override
    public void populateCache(StoredInventoryEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Populate Cache Operation is not supported in Inventory Dao");
    }

    @Override
    public void evictCache(StoredInventoryEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Evict Cache Operation is not supported in Inventory Dao");
    }

    public StoredInventoryEntity findByInventoryId(String inventoryId) {
        return inventoryMySqlRepository.findByInventoryId(inventoryId);
    }

    public StoredInventoryEntity findByProductId(String productId) {
        return inventoryMySqlRepository.findByProduct_ProductId(productId);
    }
}
