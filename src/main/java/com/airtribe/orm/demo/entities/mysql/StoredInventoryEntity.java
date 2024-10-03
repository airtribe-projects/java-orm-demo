package com.airtribe.orm.demo.entities.mysql;

import com.airtribe.orm.demo.entities.base.BaseEntity;
import com.airtribe.orm.demo.enums.InventoryUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventories")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredInventoryEntity extends BaseEntity {

    @Column(name = "inventory_id", unique = true, nullable = false)
    private String inventoryId;

    @Column(name = "inventory_name", nullable = false)
    private String inventoryName;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "inventory_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryUnit inventoryUnit;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private StoredProductEntity product;
}
