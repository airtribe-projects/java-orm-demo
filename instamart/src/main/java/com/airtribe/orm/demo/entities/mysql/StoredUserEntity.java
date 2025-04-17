package com.airtribe.orm.demo.entities.mysql;

import com.airtribe.orm.demo.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class StoredUserEntity extends BaseEntity {
    @Column(name = "user_account_id", unique = true, nullable = false)
    private String userAccountId;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StoredOrdersEntity> orders = new ArrayList<>();
}