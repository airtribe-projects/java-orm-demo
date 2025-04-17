package com.airtribe.orm.demo.models.Response;

import com.airtribe.orm.demo.entities.BaseCacheEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CachedIfscDetails extends BaseCacheEntity {
    private String micr;
    private String branch;
    private String address;
    private String state;
    private String contact;
    private String city;
    private String district;
    private String bankCode;
    private String ifsc;
    private String baknName;
}

