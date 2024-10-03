package com.airtribe.orm.demo.models.responses.inventories;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InventoryDeleteResponseDto implements BaseResponse {
    private String inventoryName;
    private boolean isDeleted;
    private String message;
}
