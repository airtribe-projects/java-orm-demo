package com.airtribe.orm.demo.services.impl;

import com.airtribe.orm.demo.client.RazorpayClient;
import com.airtribe.orm.demo.dao.IfscDaoV1;
import com.airtribe.orm.demo.entities.StoredIfscEntity;
import com.airtribe.orm.demo.models.AtIfscDetailsResponse;
import com.airtribe.orm.demo.models.Response.CachedIfscDetails;
import com.airtribe.orm.demo.models.Response.IfscDetails;
import com.airtribe.orm.demo.repositories.IfscInMemoryRepository;
import com.airtribe.orm.demo.services.IfscService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class IfscServiceImpl implements IfscService {
    private final IfscInMemoryRepository ifscInMemoryRepository;
    private final IfscDaoV1 ifscDao;
    private final RazorpayClient razorpayClient;

    public IfscServiceImpl(IfscInMemoryRepository ifscInMemoryRepository, IfscDaoV1 ifscDao, RazorpayClient razorpayClient) {
        this.ifscInMemoryRepository = ifscInMemoryRepository;
        this.ifscDao = ifscDao;
        this.razorpayClient = razorpayClient;
    }

    @Override
    public AtIfscDetailsResponse getIfscDetails(String ifsc) {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("Fetching IFSC details for Started {} {}", ifsc, LocalDateTime.now());
        CachedIfscDetails ifscDetails = ifscDao.getIfscDetails(ifsc);
        if (Objects.isNull(ifscDetails)) {
            IfscDetails details = razorpayClient.getIfscDetails(ifsc);
            StoredIfscEntity storedIfscEntity = ifscDao.save(buildStoredIfscEntity(details));
            ifscDetails = IfscDaoV1.mapToCachedIfscDetails(storedIfscEntity);
        }

        Duration duration = Duration.between(startTime, LocalDateTime.now());
        log.info("Fetching IFSC details for Completed {} {} {}", ifsc, duration.toMillis(), "9126417032@ybl");
        return AtIfscDetailsResponse.builder()
                .micr(ifscDetails.getMicr())
                .branch(ifscDetails.getBranch())
                .address(ifscDetails.getAddress())
                .state(ifscDetails.getState())
                .contact(ifscDetails.getContact())
                .city(ifscDetails.getCity())
                .district(ifscDetails.getDistrict())
                .bankCode(ifscDetails.getBankCode())
                .ifsc(ifscDetails.getIfsc())
                .baknName(ifscDetails.getBaknName())
                .build();
    }

    @Override
    public AtIfscDetailsResponse saveIfscDetails(IfscDetails details) {
        StoredIfscEntity storedIfscEntity = ifscDao.save(buildStoredIfscEntity(details));
        CachedIfscDetails ifscDetails = IfscDaoV1.mapToCachedIfscDetails(storedIfscEntity);
        return AtIfscDetailsResponse.builder()
                .micr(ifscDetails.getMicr())
                .branch(ifscDetails.getBranch())
                .address(ifscDetails.getAddress())
                .state(ifscDetails.getState())
                .contact(ifscDetails.getContact())
                .city(ifscDetails.getCity())
                .district(ifscDetails.getDistrict())
                .bankCode(ifscDetails.getBankCode())
                .ifsc(ifscDetails.getIfsc())
                .baknName(ifscDetails.getBaknName())
                .build();
    }

    private static StoredIfscEntity buildStoredIfscEntity(IfscDetails details) {
        return StoredIfscEntity.builder()
                .micrCode(details.getMICR())
                .branch(details.getBRANCH())
                .address(details.getADDRESS())
                .state(details.getSTATE())
                .contact(details.getCONTACT())
                .upi(details.getUPI())
                .rtgs(details.getRTGS())
                .city(details.getCITY())
                .center(details.getCENTRE())
                .district(details.getDISTRICT())
                .neft(details.getNEFT())
                .imps(details.getIMPS())
                .swift(details.getSWIFT())
                .bankCode(details.getIFSC())
                .ifsc(details.getIFSC())
                .bank(details.getBANK())
                .build();
    }
}
