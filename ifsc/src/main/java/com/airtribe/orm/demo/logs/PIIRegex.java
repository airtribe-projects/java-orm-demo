package com.airtribe.orm.demo.logs;

public class PIIRegex {
  private static final String PHONE_REGEX = "\\b(\\d{10})\\b";
  private static final String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
  private static final String AADHAR_REGEX = "\\b\\d{4}\\s?\\d{4}\\s?\\d{4}\\b";
  private static final String PAN_REGEX = "\\b[A-Z]{5}[0-9]{4}[A-Z]{1}\\b";
  private static final String BANK_ACC_REGEX = "\\b\\d{9,18}\\b";
  private static final String UPI_VPA_REGEX = "\\b[a-zA-Z0-9.-]+@[a-zA-Z]+\\b";
  private static final String PIPE = "|";

  public static String getAllPiiRegex() {
    return PHONE_REGEX + PIPE + EMAIL_REGEX + PIPE + AADHAR_REGEX + PIPE + PAN_REGEX + PIPE + BANK_ACC_REGEX + PIPE + UPI_VPA_REGEX;
  }
}
