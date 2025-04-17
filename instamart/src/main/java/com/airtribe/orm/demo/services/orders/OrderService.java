package com.airtribe.orm.demo.services.orders;


import com.airtribe.orm.demo.models.requests.orders.CreateOrderRequest;
import com.airtribe.orm.demo.models.requests.orders.UpdateOrderRequest;
import com.airtribe.orm.demo.models.responses.orders.OrderDetailsResponse;

public interface OrderService {
    OrderDetailsResponse createOrder(CreateOrderRequest createOrderRequest);
    OrderDetailsResponse updateOrderStatus(UpdateOrderRequest updateOrderRequest);
    OrderDetailsResponse deleteOrder(String userId, String orderId);
    OrderDetailsResponse getOrder(String userId, String orderId);
}
