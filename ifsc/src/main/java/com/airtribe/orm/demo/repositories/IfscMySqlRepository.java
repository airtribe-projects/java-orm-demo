package com.airtribe.orm.demo.repositories;

import com.airtribe.orm.demo.entities.StoredIfscEntity;
import com.airtribe.orm.demo.entities.mysql.StoredOrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IfscMySqlRepository extends JpaRepository<StoredIfscEntity, Long> {

    StoredIfscEntity findByIfsc(String ifsc);

}
