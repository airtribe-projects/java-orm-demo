package com.airtribe.orm.demo.models.responses.users;

import com.airtribe.orm.demo.models.responses.base.BaseResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDetailsResponseDto implements BaseResponse {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
}
