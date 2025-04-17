package com.airtribe.orm.demo.entities.redis;

import com.airtribe.orm.demo.entities.BaseCacheEntity;
import com.airtribe.orm.demo.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CachedOrdersEntity extends BaseCacheEntity {
    private String orderId;

    private LocalDateTime orderDate;

    private double orderValue;

    private OrderStatus orderStatus;

    private boolean isDeleted;

    private String userId;

    private List<String> productIds;
}
