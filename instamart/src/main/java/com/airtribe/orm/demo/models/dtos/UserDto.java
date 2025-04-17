package com.airtribe.orm.demo.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
}
