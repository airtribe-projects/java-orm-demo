package com.airtribe.orm.demo.controllers;

import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/health")
public class HealthController {

    @GetMapping(path = "/is-alive", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Check The health of the service", description = "Check if the service is up and running")
    public GenericResponse<BaseResponse> isAlive() {
        return GenericResponse.builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.HEALTH_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.HEALTH_SUCCESS_RESPONSE_MESSAGE)
                .build();
    }
}
