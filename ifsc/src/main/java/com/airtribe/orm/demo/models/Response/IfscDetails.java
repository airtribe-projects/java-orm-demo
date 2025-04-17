package com.airtribe.orm.demo.models.Response;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IfscDetails implements BaseResponse {
    private String MICR;
    private String BRANCH;
    private String ADDRESS;
    private String STATE;
    private String CONTACT;
    private String UPI;
    private String RTGS;
    private String CITY;
    private String CENTRE;
    private String DISTRICT;
    private String NEFT;
    private String IMPS;
    private String SWIFT;
    private String BANKCODE;
    private String IFSC;
    private String ISO3166;
    private String BANK;
}

