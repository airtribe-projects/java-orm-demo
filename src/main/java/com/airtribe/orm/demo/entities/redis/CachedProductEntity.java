package com.airtribe.orm.demo.entities.redis;

import com.airtribe.orm.demo.entities.base.BaseCacheEntity;
import com.airtribe.orm.demo.entities.mysql.StoredInventoryEntity;
import com.airtribe.orm.demo.entities.mysql.StoredOrdersEntity;
import com.airtribe.orm.demo.enums.InventoryUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
