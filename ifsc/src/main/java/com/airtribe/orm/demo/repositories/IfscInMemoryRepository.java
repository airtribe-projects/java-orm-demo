package com.airtribe.orm.demo.repositories;

import com.airtribe.orm.demo.models.Response.CachedIfscDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class IfscInMemoryRepository {

    private static ConcurrentHashMap<String, CachedIfscDetails> IFSC_DETAILS_CONCURRENT_HASH_MAP;

    private ConcurrentHashMap<String, CachedIfscDetails> getIfscDetailsConcurrentHashMap() {
        if (CollectionUtils.isEmpty(IFSC_DETAILS_CONCURRENT_HASH_MAP)) {
            IFSC_DETAILS_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
        }
        return IFSC_DETAILS_CONCURRENT_HASH_MAP;
    }

    public CachedIfscDetails get(String ifsc) {
        return getIfscDetailsConcurrentHashMap().get(ifsc);
    }

    public void put(String ifsc, CachedIfscDetails IfscDetails) {
        getIfscDetailsConcurrentHashMap().put(ifsc, IfscDetails);
    }

    public void invalidate(String ifsc) {
        getIfscDetailsConcurrentHashMap().remove(ifsc);
    }

    public void invalidateAll() {
        getIfscDetailsConcurrentHashMap().clear();
    }
}

