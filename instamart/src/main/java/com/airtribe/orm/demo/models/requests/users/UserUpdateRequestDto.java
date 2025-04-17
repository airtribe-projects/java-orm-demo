package com.airtribe.orm.demo.models.requests.users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserUpdateRequestDto {
    private String userId;
    private String email;
    private String phoneNumber;
}
