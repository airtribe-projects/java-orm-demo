package com.airtribe.orm.demo.models.responses.orders;

import com.airtribe.orm.demo.enums.OrderStatus;
import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import com.airtribe.orm.demo.models.responses.products.ProductDetailsResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDetailsResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDetailsResponse implements BaseResponse {
    private String orderId;
    private String userId;
    private double totalPrice;
    private OrderStatus orderStatus;
    private List<ProductDetailsResponseDto> products;
    private UserDetailsResponseDto user;
}
