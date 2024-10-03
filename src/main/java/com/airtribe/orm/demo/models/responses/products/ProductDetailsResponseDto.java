package com.airtribe.orm.demo.models.responses.products;

import com.airtribe.orm.demo.enums.InventoryUnit;
import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsResponseDto implements BaseResponse {
    private String productId;
    private String name;
    private Double price;
    private InventoryUnit unit;
}
