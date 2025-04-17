package com.airtribe.orm.demo.models;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AtIfscDetailsResponse implements BaseResponse {
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

