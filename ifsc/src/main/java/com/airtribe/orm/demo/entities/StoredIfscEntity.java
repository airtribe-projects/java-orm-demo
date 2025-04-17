package com.airtribe.orm.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "ifsc_details",
        indexes = {
                @Index(name = "idx_ifsc", columnList = "ifsc")
        })
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredIfscEntity extends BaseEntity {
    private String micrCode;
    private String branch;
    private String address;
    private String state;
    private String contact;
    private String upi;
    private String rtgs;
    private String city;
    private String center;
    private String district;
    private String neft;
    private String imps;
    private String swift;
    private String bankCode;
    private String ifsc;
    private String bank;
}
