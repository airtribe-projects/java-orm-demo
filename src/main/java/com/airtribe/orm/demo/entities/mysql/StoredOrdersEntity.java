package com.airtribe.orm.demo.entities.mysql;

import com.airtribe.orm.demo.entities.base.BaseEntity;
import com.airtribe.orm.demo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "orders",
        indexes = {
                @Index(name = "idx_user_id", columnList = "user_account_id")
        })
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredOrdersEntity extends BaseEntity {

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "order_value", nullable = false)
    private double orderValue;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_id", nullable = false, referencedColumnName = "user_account_id")
    private StoredUserEntity user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    )
    private List<StoredProductEntity> products = new ArrayList<>();
}
