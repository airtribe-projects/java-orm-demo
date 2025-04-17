package com.airtribe.orm.demo.daos;

import com.airtribe.orm.demo.Exceptions.UnsupportedException;
import com.airtribe.orm.demo.entities.mysql.StoredUserEntity;
import com.airtribe.orm.demo.models.dtos.UserDto;
import com.airtribe.orm.demo.repositories.mySql.UserMySqlRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao implements BaseDao<StoredUserEntity> {

    private final UserMySqlRepository userMySqlRepository;
    // UserRedisRepository userRedisRepository;

    public UserDao(UserMySqlRepository userMySqlRepository) {
        this.userMySqlRepository = userMySqlRepository;
    }

    @Override
    public StoredUserEntity save(StoredUserEntity entity) {
        return userMySqlRepository.save(entity);
    }

    @Override
    public StoredUserEntity saveAndFlush(StoredUserEntity entity) {
        return userMySqlRepository.saveAndFlush(entity);
    }

    @Override
    public List<StoredUserEntity> saveAll(List<StoredUserEntity> entities) {
        return userMySqlRepository.saveAll(entities);
    }

    @Override
    public List<StoredUserEntity> saveAllAndFlush(List<StoredUserEntity> entities) {
        return userMySqlRepository.saveAllAndFlush(entities);
    }

    @Override
    public void populateCache(StoredUserEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Populate Cache Operation is not supported in User Dao");
    }

    @Override
    public void evictCache(StoredUserEntity entity) {
        throw new UnsupportedException("UNSUPPORTED_OPERATION", "Evict Cache Operation is not supported in User Dao");
    }

    public StoredUserEntity findByUserId(String userId) {
        return userMySqlRepository.findUserDetails(userId);
    }

    public StoredUserEntity findByEmail(String email) {
        return userMySqlRepository.findByEmail(email);
    }

    public StoredUserEntity findByPhoneNumber(String phoneNumber) {
        return userMySqlRepository.findByPhoneNumber(phoneNumber);
    }

    public StoredUserEntity deleteUser(String userId) {
        StoredUserEntity storedUserEntity = findByUserId(userId);
        try {
            userMySqlRepository.delete(storedUserEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user");
        }
        return storedUserEntity;
    }

    public UserDto getUserDetails(String userId) {
        // first look into cache

        //if cache is present map the data into UserDto and return it

        // if cache is not present look into DB

        // save the dta in cache

        // map the data to userDto and return it

        StoredUserEntity storedUserEntity = findByUserId(userId);

        return UserDto.builder()
                .userId(storedUserEntity.getUserAccountId())
                .userName(storedUserEntity.getUsername())
                .email(storedUserEntity.getEmail())
                .phoneNumber(storedUserEntity.getPhoneNumber())
                .address("Dummy Address")
                .build();
    }
}
