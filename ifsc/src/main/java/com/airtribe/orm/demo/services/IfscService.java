package com.airtribe.orm.demo.services;


import com.airtribe.orm.demo.models.AtIfscDetailsResponse;
import com.airtribe.orm.demo.models.Response.IfscDetails;

public interface IfscService {

    AtIfscDetailsResponse getIfscDetails(String ifsc);

    AtIfscDetailsResponse saveIfscDetails(IfscDetails details);

}