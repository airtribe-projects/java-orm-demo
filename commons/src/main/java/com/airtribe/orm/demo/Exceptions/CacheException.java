package com.airtribe.orm.demo.Exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Builder
public class CacheException extends RuntimeException {
  private final String errorCode;
  private final String errorMessage;
  private final Throwable cause;

  @Builder
  public CacheException(String errorCode, String errorMessage, Throwable cause) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.cause = cause;

    log.error("InvalidInputException: errorCode: {}, errorMessage: {}, errorReason {}", this.errorCode, this.errorMessage, this.cause);
  }
}
