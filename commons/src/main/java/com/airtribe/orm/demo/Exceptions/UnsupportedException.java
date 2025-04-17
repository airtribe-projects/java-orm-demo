package com.airtribe.orm.demo.Exceptions;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnsupportedException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    @Builder
    public UnsupportedException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

        log.error("UnsupportedException: errorCode: {}, errorMessage: {}", this.errorCode, this.errorMessage);
    }
}