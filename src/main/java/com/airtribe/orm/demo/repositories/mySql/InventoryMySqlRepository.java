package com.airtribe.orm.demo.repositories.mySql;

import com.airtribe.orm.demo.entities.mysql.StoredInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMySqlRepository extends JpaRepository<StoredInventoryEntity, Long> {

    StoredInventoryEntity findByInventoryId(String inventoryId);

    @Query(value = "SELECT * FROM inventories WHERE inventory_id = ?1 and is_deleted = 0", nativeQuery = true)
    StoredInventoryEntity getInventoryDetails(String inventoryId);

    @Query(value = "SELECT * FROM inventories WHERE product_id = ?1 and is_deleted = 0", nativeQuery = true)
    StoredInventoryEntity findByProductId(String productId);

    StoredInventoryEntity findByProduct_ProductId(String productId);

}
