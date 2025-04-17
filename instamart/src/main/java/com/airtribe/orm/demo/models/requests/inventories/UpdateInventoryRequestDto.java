package com.airtribe.orm.demo.models.requests.inventories;

import com.airtribe.orm.demo.enums.InventoryUnit;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateInventoryRequestDto {
    private String inventoryId;
    private int stock;
    private InventoryUnit inventoryUnit;
    private String inventoryName;
}
