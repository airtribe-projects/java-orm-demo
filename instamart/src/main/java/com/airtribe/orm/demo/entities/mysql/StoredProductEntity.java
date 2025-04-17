package com.airtribe.orm.demo.entities.mysql;

import com.airtribe.orm.demo.entities.BaseEntity;
import com.airtribe.orm.demo.enums.InventoryUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredProductEntity extends BaseEntity {

    @Column(name = "product_id", unique = true, nullable = false)
    private String productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private InventoryUnit unit;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private List<StoredOrdersEntity> orders = new ArrayList<>();

    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER)
    private StoredInventoryEntity inventory;
}
