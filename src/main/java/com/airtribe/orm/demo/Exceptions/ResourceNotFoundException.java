package com.airtribe.orm.demo.Exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Data
public class ResourceNotFoundException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    @Builder
    public ResourceNotFoundException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

        log.error("InvalidInputException: errorCode: {}, errorMessage: {}", this.errorCode, this.errorMessage);
    }
}