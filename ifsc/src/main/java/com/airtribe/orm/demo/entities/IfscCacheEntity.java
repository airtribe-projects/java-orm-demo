package com.airtribe.orm.demo.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class IfscCacheEntity extends BaseCacheEntity {
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
