package com.airtribe.orm.demo.services.users;

import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.daos.UserDao;
import com.airtribe.orm.demo.entities.mysql.StoredUserEntity;
import com.airtribe.orm.demo.models.requests.users.UserCreationRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.users.UserDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDetailsResponseDto;
import com.airtribe.orm.demo.utils.UserMappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMappers userMappers;

    public UserServiceImpl(UserDao userDao, UserMappers userMappers) {
        this.userDao = userDao;
        this.userMappers = userMappers;
    }

    @Override
    public UserDetailsResponseDto createUser(UserCreationRequestDto userCreationRequestDto) {
        StoredUserEntity storedUserEntity = userMappers.mapToUserEntity(userCreationRequestDto);
        storedUserEntity = userDao.save(storedUserEntity);
        return userMappers.mapToUserDetailsResponse(storedUserEntity);
    }

    @Override
    public UserDetailsResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        StoredUserEntity storedUserEntity = getUserDetails(userUpdateRequestDto.getUserId());
        storedUserEntity.setEmail(userUpdateRequestDto.getEmail());
        storedUserEntity.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
        storedUserEntity = userDao.save(storedUserEntity);
        return userMappers.mapToUserDetailsResponse(storedUserEntity);
    }

    @Override
    public UserDeleteResponseDto deleteUser(String userId) {
        StoredUserEntity storedUserEntity = getUserDetails(userId);
        storedUserEntity.setDeleted(true);
        storedUserEntity = userDao.save(storedUserEntity);
        return userMappers.mapToUserDeleteResponseDto(storedUserEntity);
    }

    @Override
    public UserDeleteResponseDto forceDeleteUser(String userId) {
        StoredUserEntity storedUserEntity = userDao.deleteUser(userId);
        return userMappers.mapToUserDeleteResponseDto(storedUserEntity);
    }


    @Override
    public UserDetailsResponseDto getUser(String userId) {
        StoredUserEntity storedUserEntity = getUserDetails(userId);
        return userMappers.mapToUserDetailsResponse(storedUserEntity);
    }

    private StoredUserEntity getUserDetails(String userId) {
        StoredUserEntity storedUserEntity = userDao.findByUserId(userId);
        if (Objects.isNull(storedUserEntity)) {
            throw ResourceNotFoundException.builder()
                    .errorCode("USER_NOT_FOUND")
                    .errorMessage("User not found")
                    .build();
        }
        return storedUserEntity;
    }
}
