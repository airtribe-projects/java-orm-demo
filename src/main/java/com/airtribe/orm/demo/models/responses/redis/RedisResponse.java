package com.airtribe.orm.demo.models.responses.redis;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedisResponse implements BaseResponse {
    private String result;
}
