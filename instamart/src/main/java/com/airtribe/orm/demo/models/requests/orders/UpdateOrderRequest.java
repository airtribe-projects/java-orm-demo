package com.airtribe.orm.demo.models.requests.orders;

import com.airtribe.orm.demo.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderRequest {
    private String userId;
    private String orderId;
    private OrderStatus orderStatus;
}
