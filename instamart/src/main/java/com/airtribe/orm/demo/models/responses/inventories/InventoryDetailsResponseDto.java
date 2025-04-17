package com.airtribe.orm.demo.models.responses.inventories;

import com.airtribe.orm.demo.enums.InventoryUnit;
import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import com.airtribe.orm.demo.models.responses.products.ProductDetailsResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InventoryDetailsResponseDto implements BaseResponse {
    private String inventoryId;
    private String inventoryName;
    private double stock;
    private InventoryUnit inventoryUnit;
    private ProductDetailsResponseDto productDetail;
}
