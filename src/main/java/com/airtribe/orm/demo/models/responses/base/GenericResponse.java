package com.airtribe.orm.demo.models.responses.base;

import com.airtribe.orm.demo.enums.ResponseType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenericResponse<T extends BaseResponse> {
    private ResponseType responseType;
    private String responseCode;
    private String responseMessage;
    private T data;
}
