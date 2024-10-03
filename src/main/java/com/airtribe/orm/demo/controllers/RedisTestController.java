package com.airtribe.orm.demo.controllers;

import com.airtribe.orm.demo.Exceptions.CacheException;
import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.distributed_cache.manager.CacheManager;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import com.airtribe.orm.demo.models.responses.redis.RedisResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/test/redis")
public class RedisTestController {
    private final CacheManager cacheManager;

    public RedisTestController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("/hasKey")
    @Operation(summary = "Check if key exists in cache")
    public GenericResponse<RedisResponse> hasKey(@RequestParam String key) {
        try {
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.SUCCESS)
                    .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                    .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                    .data(RedisResponse.builder()
                            .result(cacheManager.hasKey(key)
                                    .toString())
                            .build())
                    .build();
        } catch (CacheException e) {
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.SUCCESS)
                    .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                    .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                    .data(RedisResponse.builder()
                            .result(cacheManager.hasKey(key)
                                    .toString())
                            .build())
                    .build();
        }
    }

    @PostMapping("/save")
    @Operation(summary = "Save data to cache")
    public GenericResponse<RedisResponse> save(@RequestParam String key, @RequestParam String value) {
        try {
            cacheManager.saveWithoutTTl(key, value);
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.SUCCESS)
                    .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                    .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                    .data(RedisResponse.builder()
                            .result("Data saved successfully")
                            .build())
                    .build();
        } catch (CacheException e) {
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .data(RedisResponse.builder()
                            .result("Error saving data")
                            .build())
                    .build();
        }
    }

    @PostMapping("/remove")
    @Operation(summary = "Remove data from cache")
    public GenericResponse<RedisResponse> remove(@RequestParam String key) {
        try {
            cacheManager.remove(key);
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.SUCCESS)
                    .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                    .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                    .data(RedisResponse.builder()
                            .result("Data removed successfully")
                            .build())
                    .build();
        } catch (CacheException e) {
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .data(RedisResponse.builder()
                            .result("Error removing data")
                            .build())
                    .build();
        }
    }

    @GetMapping("/get")
    @Operation(summary = "get the value of the key")
    public GenericResponse<RedisResponse> getData(@RequestParam String key) {
        try {
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.SUCCESS)
                    .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                    .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                    .data(RedisResponse.builder()
                            .result(cacheManager.get(key, String.class))
                            .build())
                    .build();
        } catch (CacheException e) {
            return GenericResponse.<RedisResponse>builder()
                    .responseType(ResponseType.SUCCESS)
                    .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                    .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                    .data(null)
                    .build();
        }
    }

}
