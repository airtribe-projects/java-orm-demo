package com.airtribe.orm.demo.utils;

import com.airtribe.orm.demo.entities.mysql.StoredOrdersEntity;
import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.entities.mysql.StoredUserEntity;
import com.airtribe.orm.demo.enums.OrderStatus;
import com.airtribe.orm.demo.models.requests.orders.CreateOrderRequest;
import com.airtribe.orm.demo.models.responses.orders.DeleteOrderResponse;
import com.airtribe.orm.demo.models.responses.orders.OrderDetailsResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class OrderMappers {
    private final ProductMappers productMappers;
    private final UserMappers userMappers;

    public OrderMappers(ProductMappers productMappers, UserMappers userMappers) {
        this.productMappers = productMappers;
        this.userMappers = userMappers;
    }

    public StoredOrdersEntity mapToOrderEntity(CreateOrderRequest createOrderRequest, StoredUserEntity storedUserEntity, List<StoredProductEntity> storedProductEntities) {
        return StoredOrdersEntity.builder()
                .orderId(UUID.randomUUID().toString())
                .user(storedUserEntity)
                .products(storedProductEntities)
                .orderStatus(OrderStatus.INITIATED)
                .orderDate(LocalDateTime.now())
                .isDeleted(false)
                .orderValue(calculateOrderValue(storedProductEntities))
                .build();
    }

    private double calculateOrderValue(List<StoredProductEntity> storedProductEntities) {
        return storedProductEntities.stream().mapToDouble(StoredProductEntity::getPrice).sum();
    }

    public OrderDetailsResponse mapToOrderDetailsResponse(StoredOrdersEntity storedOrdersEntity) {
        return OrderDetailsResponse.builder()
                .orderId(storedOrdersEntity.getOrderId())
                .userId(storedOrdersEntity.getUser().getUserAccountId())
                .totalPrice(storedOrdersEntity.getOrderValue())
                .orderStatus(storedOrdersEntity.getOrderStatus())
                .products(storedOrdersEntity.getProducts().stream().map(productMappers::mapToProductDetailsResponse).toList())
                .user(userMappers.mapToUserDetailsResponse(storedOrdersEntity.getUser()))
                .build();
    }

    public DeleteOrderResponse mapToOrderDeleteResponseDto(StoredOrdersEntity storedOrdersEntity) {
        return DeleteOrderResponse.builder()
                .orderId(storedOrdersEntity.getOrderId())
                .userId(storedOrdersEntity.getUser().getUserAccountId())
                .totalPrice(storedOrdersEntity.getOrderValue())
                .orderStatus(storedOrdersEntity.getOrderStatus())
                .products(storedOrdersEntity.getProducts().stream().map(productMappers::mapToProductDetailsResponse).toList())
                .isDeleted(storedOrdersEntity.isDeleted())
                .message("Order deleted successfully")
                .build();
    }
}
