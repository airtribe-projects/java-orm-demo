package com.airtribe.orm.demo.repositories.mySql;

import com.airtribe.orm.demo.entities.mysql.StoredOrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMySqlRepository extends JpaRepository<StoredOrdersEntity, Long> {
    List<StoredOrdersEntity> findAllByUser_UserAccountId(String userId);

    @Query(value = "SELECT * FROM orders WHERE user_account_id = ?1 AND order_id = ?2 and is_deleted = 0", nativeQuery = true)
    StoredOrdersEntity findByUserIdAndOrderId(String userId, String orderId);

}
