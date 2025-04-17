package com.airtribe.orm.demo.entities.redis;

import com.airtribe.orm.demo.entities.BaseCacheEntity;
import com.airtribe.orm.demo.enums.InventoryUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CachedProductEntity extends BaseCacheEntity {
    private String productId;

    private String name;

    private Double price;

    private InventoryUnit unit;

    private boolean isDeleted;

    private String inventoryId;
}
