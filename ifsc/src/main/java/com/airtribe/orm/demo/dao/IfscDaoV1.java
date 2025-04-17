package com.airtribe.orm.demo.dao;

import com.airtribe.orm.demo.daos.BaseDao;
import com.airtribe.orm.demo.entities.StoredIfscEntity;
import com.airtribe.orm.demo.models.Response.CachedIfscDetails;
import com.airtribe.orm.demo.repositories.IfscInMemoryRepository;
import com.airtribe.orm.demo.repositories.IfscMySqlRepository;
import com.airtribe.orm.demo.repositories.IfscRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class IfscDaoV1 implements BaseDao<StoredIfscEntity> {
    @Autowired
    private IfscMySqlRepository ifscMySqlRepository;
    @Autowired
    private IfscInMemoryRepository ifscInMemoryRepository;
    @Autowired
    private IfscRedisRepository ifscRedisRepository;

    public CachedIfscDetails getIfscDetails(String ifsc) {
        //get details from in memory cache
        CachedIfscDetails cachedIfscDetails = ifscInMemoryRepository.get(ifsc);

        //if not found in memory cache, get from redis cache
        if (Objects.isNull(cachedIfscDetails)) {
            log.info("IFSC is not present in memory cache: {}", ifsc);
            cachedIfscDetails = ifscRedisRepository.get(ifsc);
            //if not found in redis cache, get from mysql
            if (Objects.isNull(cachedIfscDetails)) {
                log.info("IFSC is not present in the Redis cache: {}", ifsc);
                StoredIfscEntity ifscDetails = ifscMySqlRepository.findByIfsc(ifsc);
                if (Objects.nonNull(ifscDetails)) {
                    cachedIfscDetails = mapToCachedIfscDetails(ifscDetails);

                    //save in memory cache
                    ifscInMemoryRepository.put(ifsc, cachedIfscDetails);
                    ifscRedisRepository.save(ifsc, cachedIfscDetails);
                    log.info("Saving the ifsc in both in memory and Redis Cache: {}", ifsc);
                } else {
                    log.info("Ifsc not found in mySql also: {}", ifsc);
                }
            } else {
                log.info("Got the IFSC Details From my In Redis cache: {}", ifsc);
                ifscInMemoryRepository.put(ifsc, cachedIfscDetails);
            }
        } else {
            log.info("Got the IFSC Details From my In memory cache: {}", ifsc);
        }

        return cachedIfscDetails;
    }

    public static CachedIfscDetails mapToCachedIfscDetails(StoredIfscEntity ifscDetails) {
        return CachedIfscDetails.builder()
                .micr(ifscDetails.getMicrCode())
                .branch(ifscDetails.getBranch())
                .address(ifscDetails.getAddress())
                .state(ifscDetails.getState())
                .contact(ifscDetails.getContact())
                .city(ifscDetails.getCity())
                .district(ifscDetails.getDistrict())
                .bankCode(ifscDetails.getBankCode())
                .ifsc(ifscDetails.getIfsc())
                .baknName(ifscDetails.getBank())
                .build();
    }

    @Override
    public StoredIfscEntity save(StoredIfscEntity entity) {
        evictCache(entity);
        return ifscMySqlRepository.save(entity);
    }

    @Override
    public StoredIfscEntity saveAndFlush(StoredIfscEntity entity) {
        return ifscMySqlRepository.saveAndFlush(entity);
    }

    @Override
    public List<StoredIfscEntity> saveAll(List<StoredIfscEntity> entities) {
        return ifscMySqlRepository.saveAll(entities);
    }

    @Override
    public List<StoredIfscEntity> saveAllAndFlush(List<StoredIfscEntity> entities) {
        return ifscMySqlRepository.saveAllAndFlush(entities);
    }

    @Override
    public void populateCache(StoredIfscEntity entity) {

    }

    @Override
    public void evictCache(StoredIfscEntity entity) {
        ifscInMemoryRepository.invalidate(entity.getIfsc());
    }
}
