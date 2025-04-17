package com.airtribe.orm.demo.client;

import com.airtribe.orm.demo.models.Response.IfscDetails;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface RazorpayClient {

    @RequestLine("GET /{ifsc}")
    @Headers({"Content-Type:application/json", "Authorization:{authorization}"})
    IfscDetails getIfscDetails(@Param("ifsc") String ifsc);

}