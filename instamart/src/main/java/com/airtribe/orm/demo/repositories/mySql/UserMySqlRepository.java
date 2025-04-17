package com.airtribe.orm.demo.repositories.mySql;

import com.airtribe.orm.demo.entities.mysql.StoredUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMySqlRepository extends JpaRepository<StoredUserEntity, Long> {

    // select * from users where user_account_id = ?1
    StoredUserEntity findByUserAccountId(String userId);

    // select * from users where email = ?1
    StoredUserEntity findByEmail(String email);

    // select * from users where phone_number = ?1
    StoredUserEntity findByPhoneNumber(String mobile);

    @Query(value = "SELECT * FROM users WHERE user_account_id = ?1 and is_deleted = false", nativeQuery = true)
    StoredUserEntity findUserDetails(String userId);

}
