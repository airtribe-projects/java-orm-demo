package com.airtribe.orm.demo.caffiene_factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseCaffeineCachePayload<T> {
    private T data;
    private long expirationTime;
}
