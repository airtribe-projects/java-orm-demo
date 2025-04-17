package com.airtribe.orm.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    INITIATED,
    PENDING,
    COMPLETED,
    FAILED
}
