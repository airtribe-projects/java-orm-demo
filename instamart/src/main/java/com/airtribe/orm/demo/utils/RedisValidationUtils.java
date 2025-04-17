package com.airtribe.orm.demo.utils;

import com.airtribe.orm.demo.Exceptions.InvalidKeyException;
import org.springframework.util.StringUtils;

public class RedisValidationUtils {

    private RedisValidationUtils() {
    }

    public static void validateKey(String key) throws InvalidKeyException {
        if (!StringUtils.hasText(key)) {
            throw new InvalidKeyException("Key Can't be null");
        }
    }
}
