package com.airtribe.orm.demo.models.responses.users;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDeleteResponseDto implements BaseResponse {
    private String username;
    private boolean isDeleted;
    private String message;
}
