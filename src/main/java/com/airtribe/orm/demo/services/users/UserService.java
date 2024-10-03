package com.airtribe.orm.demo.services.users;

import com.airtribe.orm.demo.models.requests.users.UserCreationRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.users.UserDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDetailsResponseDto;

public interface UserService {
    UserDetailsResponseDto createUser(UserCreationRequestDto userCreationRequestDto);

    UserDetailsResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto);

    UserDeleteResponseDto deleteUser(String userId);

    UserDeleteResponseDto forceDeleteUser(String userId);

    UserDetailsResponseDto getUser(String userId);
}
