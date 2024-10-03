package com.airtribe.orm.demo.repositories.mySql;

import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMySqlRepository extends JpaRepository<StoredProductEntity, Long> {

    @Query(value = "select * from products where product_id = ?1 and is_deleted = 0", nativeQuery = true)
    StoredProductEntity findByProductId(String productId);
}
