package com.airtribe.orm.demo.models.requests.users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCreationRequestDto {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
}
