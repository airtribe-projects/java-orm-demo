package com.airtribe.orm.demo.models.requests.orders;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderRequest {
    private String userId;
    private List<String> productIds;
}
