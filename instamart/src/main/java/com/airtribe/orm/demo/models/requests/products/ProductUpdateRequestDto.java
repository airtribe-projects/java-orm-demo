package com.airtribe.orm.demo.models.requests.products;

import com.airtribe.orm.demo.enums.InventoryUnit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdateRequestDto {
    private String productId;
    private String name;
    private Double price;
    private InventoryUnit unit;

}
