package com.airtribe.orm.demo.controllers;

import com.airtribe.orm.demo.Exceptions.InvalidInputException;
import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.AtIfscDetailsResponse;
import com.airtribe.orm.demo.models.Response.IfscDetails;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import com.airtribe.orm.demo.services.IfscService;
import com.airtribe.orm.demo.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/ifsc")
public class IfscController {

    private final IfscService ifscService;
    private final ValidationUtils validationUtils;

    public IfscController(IfscService ifscService, ValidationUtils validationUtils) {
        this.ifscService = ifscService;
        this.validationUtils = validationUtils;
    }

    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get ifsc Details", description = "Fetch the details of an IFSC code")
    public GenericResponse<AtIfscDetailsResponse> fetchUserDetails(@RequestParam("ifsc") String ifsc) {
        AtIfscDetailsResponse ifscDetails = null;
        try {
            validationUtils.validateId(ifsc);
            ifscDetails = ifscService.getIfscDetails(ifsc);
        } catch (InvalidInputException e) {
            return GenericResponse.<AtIfscDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<AtIfscDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<AtIfscDetailsResponse>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(ifscDetails)
                .build();
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "addt ifsc Details", description = "Add IFSC details")
    public GenericResponse<AtIfscDetailsResponse> addIfscDetails(@RequestBody IfscDetails details) {
        AtIfscDetailsResponse ifscDetails = null;
        try {
            validationUtils.validateId(details.getIFSC());
            ifscDetails = ifscService.saveIfscDetails(details);;
        } catch (InvalidInputException e) {
            return GenericResponse.<AtIfscDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<AtIfscDetailsResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<AtIfscDetailsResponse>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(ifscDetails)
                .build();
    }
}
