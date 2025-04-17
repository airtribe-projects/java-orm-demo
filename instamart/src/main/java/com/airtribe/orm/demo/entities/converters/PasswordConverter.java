package com.airtribe.orm.demo.entities.converters;

import io.undertow.util.HexConverter;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PasswordConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return HexConverter.convertToHexString(attribute.getBytes());
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return Arrays.toString(HexConverter.convertFromHex(dbData));
    }

}
