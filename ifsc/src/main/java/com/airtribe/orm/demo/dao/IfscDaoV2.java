package com.airtribe.orm.demo.dao;

import com.airtribe.orm.demo.client.RazorpayClient;
import com.airtribe.orm.demo.daos.BaseDao;
import com.airtribe.orm.demo.models.Response.CachedIfscDetails;
import com.airtribe.orm.demo.models.Response.IfscDetails;
import com.airtribe.orm.demo.repositories.IfscInMemoryRepository;
import com.airtribe.orm.demo.repositories.IfscRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class IfscDaoV2 implements BaseDao<IfscDetails> {

    @Autowired
    private IfscRedisRepository ifscRedisRepository;
    @Autowired
    private IfscInMemoryRepository ifscInMemoryRepository;
    @Autowired
    private RazorpayClient razorpayClient;

    public CachedIfscDetails getIfscDetails(String ifsc) {
        //get details from in memory cache
        CachedIfscDetails cachedIfscDetails = ifscInMemoryRepository.get(ifsc);

        //if not found in in memory cache, get from redis cache
        if (Objects.isNull(cachedIfscDetails)) {
            //get details from redis cache
            cachedIfscDetails = ifscRedisRepository.get(ifsc);

            //if not found in redis cache, get from razorpay api
            if (Objects.isNull(cachedIfscDetails)) {
                IfscDetails ifscDetails = razorpayClient.getIfscDetails(ifsc);
                if (Objects.nonNull(ifscDetails)) {
                    cachedIfscDetails = CachedIfscDetails.builder()
                            .micr(ifscDetails.getMICR())
                            .branch(ifscDetails.getBRANCH())
                            .address(ifscDetails.getADDRESS())
                            .state(ifscDetails.getSTATE())
                            .contact(ifscDetails.getCONTACT())
                            .city(ifscDetails.getCITY())
                            .district(ifscDetails.getDISTRICT())
                            .bankCode(ifscDetails.getBANKCODE())
                            .ifsc(ifscDetails.getIFSC())
                            .baknName(ifscDetails.getBANK())
                            .build();
                    //save in redis cache
                    ifscRedisRepository.save(ifsc, cachedIfscDetails);
                }
            }
            //save in in memory cache
            ifscInMemoryRepository.put(ifsc, cachedIfscDetails);
        }

        return cachedIfscDetails;
    }

    @Override
    public IfscDetails save(IfscDetails entity) {
        return null;
    }

    @Override
    public IfscDetails saveAndFlush(IfscDetails entity) {
        return null;
    }

    @Override
    public List<IfscDetails> saveAll(List<IfscDetails> entities) {
        return List.of();
    }

    @Override
    public List<IfscDetails> saveAllAndFlush(List<IfscDetails> entities) {
        return List.of();
    }

    @Override
    public void populateCache(IfscDetails entity) {

    }

    @Override
    public void evictCache(IfscDetails entity) {

    }
}
