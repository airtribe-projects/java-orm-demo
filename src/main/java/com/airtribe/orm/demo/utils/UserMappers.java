package com.airtribe.orm.demo.utils;

import com.airtribe.orm.demo.entities.mysql.StoredUserEntity;
import com.airtribe.orm.demo.models.requests.users.UserCreationRequestDto;
import com.airtribe.orm.demo.models.responses.users.UserDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDetailsResponseDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMappers {

    public StoredUserEntity mapToUserEntity(UserCreationRequestDto userCreationRequestDto) {
        return StoredUserEntity.builder()
                .userAccountId(UUID.randomUUID().toString())
                .username(userCreationRequestDto.getUsername())
                .email(userCreationRequestDto.getEmail())
                .phoneNumber(userCreationRequestDto.getPhoneNumber())
                .password(userCreationRequestDto.getPassword())
                .build();
    }

    public UserDetailsResponseDto mapToUserDetailsResponse(StoredUserEntity storedUserEntity) {
        return UserDetailsResponseDto.builder()
                .userId(storedUserEntity.getUserAccountId())
                .username(storedUserEntity.getUsername())
                .email(storedUserEntity.getEmail())
                .phoneNumber(storedUserEntity.getPhoneNumber())
                .build();
    }

    public UserDeleteResponseDto mapToUserDeleteResponseDto(StoredUserEntity storedUserEntity) {
        return UserDeleteResponseDto.builder()
                .username(storedUserEntity.getUsername())
                .isDeleted(storedUserEntity.isDeleted())
                .message("User deleted successfully")
                .build();
    }
}
