package com.airtribe.orm.demo.daos;

import com.airtribe.orm.demo.Exceptions.UnsupportedException;
import com.airtribe.orm.demo.daos.base.BaseDao;
import com.airtribe.orm.demo.entities.mysql.StoredOrdersEntity;
import com.airtribe.orm.demo.repositories.mySql.OrderMySqlRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDao implements BaseDao<StoredOrdersEntity> {
    private final OrderMySqlRepository orderMySqlRepository;

    public OrderDao(OrderMySqlRepository orderMySqlRepository) {
        this.orderMySqlRepository = orderMySqlRepository;
    }

    @Override
    public StoredOrdersEntity save(StoredOrdersEntity entity) {
        return orderMySqlRepository.save(entity);
    }

    @Override
    public StoredOrdersEntity saveAndFlush(StoredOrdersEntity entity) {
        return orderMySqlRepository.saveAndFlush(entity);
    }

    @Override
    public List<StoredOrdersEntity> saveAll(List<StoredOrdersEntity> entities) {
        return orderMySqlRepository.saveAll(entities);
    }

    @Override
    public List<StoredOrdersEntity> saveAllAndFlush(List<StoredOrdersEntity> entities) {
        return orderMySqlRepository.saveAllAndFlush(entities);
    }

    @Override
    public void populateCache(StoredOrdersEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Populate Cache Operation is not supported in Orders Dao");
    }

    @Override
    public void evictCache(StoredOrdersEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Evict Cache Operation is not supported in Orders Dao");
    }

    public StoredOrdersEntity findByUserIdAndOrderId(String userId, String orderId) {
        return orderMySqlRepository.findByUserIdAndOrderId(userId, orderId);
    }
}
