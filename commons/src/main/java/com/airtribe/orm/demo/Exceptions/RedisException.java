package com.airtribe.orm.demo.Exceptions;

import lombok.Builder;
import lombok.Getter;

import java.security.PrivilegedActionException;

@Getter
public class RedisException extends Exception {

  /**
   * Constructs a {@link RedisException} with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public RedisException() {
    super();
  }

  /**
   * Constructs a {@link RedisException} with the specified detail message.  The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                {@link #getMessage()} method.
   */
  public RedisException(String message) {
    super(message);
  }

  /**
   * Constructs a {@link RedisException} with the specified detail message and cause.  <p>Note that
   * the detail message associated with {@code cause} is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the
   *                {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method). (A {@code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   * @since 1.4
   */
  public RedisException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a {@link RedisException} with the specified cause and a detail message of
   * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail
   * message of {@code cause}). This constructor is useful for exceptions that are little more than
   * wrappers for other throwables (for example, {@link PrivilegedActionException}).
   *
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   *              (A {@code null} value is permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   * @since 1.4
   */
  public RedisException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a {@link RedisException} with the specified detail message, cause, suppression
   * enabled or disabled, and writable stack trace enabled or disabled.
   *
   * @param message            the detail message.
   * @param cause              the cause.  (A {@code null} value is permitted, and indicates that
   *                           the cause is nonexistent or unknown.)
   * @param enableSuppression  whether suppression is enabled or disabled
   * @param writableStackTrace whether the stack trace should be writable
   * @since 1.7
   */
  @Builder
  public RedisException(
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
