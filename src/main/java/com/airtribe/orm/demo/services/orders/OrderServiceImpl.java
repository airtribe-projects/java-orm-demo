package com.airtribe.orm.demo.services.orders;

import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.daos.OrderDao;
import com.airtribe.orm.demo.daos.ProductDao;
import com.airtribe.orm.demo.daos.UserDao;
import com.airtribe.orm.demo.entities.mysql.StoredOrdersEntity;
import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.entities.mysql.StoredUserEntity;
import com.airtribe.orm.demo.models.requests.orders.CreateOrderRequest;
import com.airtribe.orm.demo.models.requests.orders.UpdateOrderRequest;
import com.airtribe.orm.demo.models.responses.orders.OrderDetailsResponse;
import com.airtribe.orm.demo.utils.OrderMappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final OrderMappers orderMapper;

    public OrderServiceImpl(OrderDao orderDao, ProductDao productDao, UserDao userDao, OrderMappers orderMapper) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDetailsResponse createOrder(CreateOrderRequest createOrderRequest) {
        StoredUserEntity storedUserEntity = getUserDetails(createOrderRequest.getUserId());
        List<StoredProductEntity> storedProductEntities = createOrderRequest.getProductIds().stream()
                .map(this::getProductEntity)
                .toList();

        StoredOrdersEntity storedOrdersEntity = orderMapper.mapToOrderEntity(createOrderRequest, storedUserEntity, storedProductEntities);
        storedOrdersEntity = orderDao.save(storedOrdersEntity);
        return orderMapper.mapToOrderDetailsResponse(storedOrdersEntity);
    }

    @Override
    public OrderDetailsResponse updateOrderStatus(UpdateOrderRequest updateOrderRequest) {
        StoredOrdersEntity storedOrdersEntity = getOrderEntity(updateOrderRequest.getUserId(), updateOrderRequest.getOrderId());
        storedOrdersEntity.setOrderStatus(updateOrderRequest.getOrderStatus());
        StoredOrdersEntity updatedOrderEntity = orderDao.save(storedOrdersEntity);
        return orderMapper.mapToOrderDetailsResponse(updatedOrderEntity);
    }

    @Override
    public OrderDetailsResponse deleteOrder(String userId, String orderId) {
        StoredOrdersEntity storedOrdersEntity = getOrderEntity(userId, orderId);
        storedOrdersEntity.setDeleted(true);
        storedOrdersEntity = orderDao.save(storedOrdersEntity);
        return orderMapper.mapToOrderDetailsResponse(storedOrdersEntity);
    }

    @Override
    public OrderDetailsResponse getOrder(String userId, String orderId) {
        StoredOrdersEntity storedOrdersEntity = getOrderEntity(userId, orderId);
        return orderMapper.mapToOrderDetailsResponse(storedOrdersEntity);
    }

    private StoredOrdersEntity getOrderEntity(String userId, String orderId) {
        StoredOrdersEntity storedOrdersEntity = orderDao.findByUserIdAndOrderId(userId, orderId);
        if (Objects.isNull(storedOrdersEntity)) {
            throw ResourceNotFoundException.builder()
                    .errorCode("ORDER_NOT_FOUND")
                    .errorMessage("Order not found")
                    .build();
        }
        return storedOrdersEntity;
    }

    private StoredUserEntity getUserDetails(String userId) {
        StoredUserEntity storedUserEntity = userDao.findByUserId(userId);
        if (Objects.isNull(storedUserEntity)) {
            throw ResourceNotFoundException.builder()
                    .errorCode("USER_NOT_FOUND")
                    .errorMessage("User not found")
                    .build();
        }
        return storedUserEntity;
    }

    private StoredProductEntity getProductEntity(String productId) {
        StoredProductEntity storedProductEntity = productDao.findByProductId(productId);
        if (Objects.isNull(storedProductEntity)) {
            throw ResourceNotFoundException.builder()
                    .errorCode("PRODUCT_NOT_FOUND")
                    .errorMessage("PRODUCT not found")
                    .build();
        }
        return storedProductEntity;
    }
}
