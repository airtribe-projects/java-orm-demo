package com.airtribe.orm.demo.models.responses.products;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDeleteResponseDto implements BaseResponse {
    private String name;
    private boolean isDeleted;
    private String message;
}
