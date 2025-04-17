package com.airtribe.orm.demo.configs;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.util.Arrays;

@Configuration
@Slf4j
@ConditionalOnExpression("'${cache.provider:NA}' == 'REDIS' and '${cache.type:NA}' == 'STANDALONE'")
public class RedisStandAloneConfig {
    //-- Redis Configurations
    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.port}")
    private Integer port;

    //-- Redis Connection Pool Configurations
    @Value("${redis.connectTimeout:200}")
    private Integer connectTimeout;
    @Value("${redis.readTimeout:200}")
    private Integer readTimeout;
    @Value("${redis.pool.maxTotal:100}")
    private Integer maxTotal;
    @Value("${redis.pool.minIdle:10}")
    private Integer minIdle;
    @Value("${redis.pool.maxIdle:20}")
    private Integer maxIdle;
    @Value("${redis.pool.worker:32}")
    private Integer workerThreads;
    @Value("${redis.pool.io:16}")
    private Integer ioThreads;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
                                                      LettucePoolingClientConfiguration lettucePoolingClientConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
    }

    @Bean
    GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        String nodeDetails[] = hostname.split(":");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(nodeDetails[0], Integer.parseInt(nodeDetails[1]));
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setDatabase(0);
        return redisStandaloneConfiguration;
    }

    @Bean
    public LettucePoolingClientConfiguration getLettucePoolConfig() {
        log.info("Creating Lettuce Pool Config with maxIdle: {}, maxTotal: {}, minIdle: {} ioThreads: {} workerThreads: {}",
                maxIdle, maxTotal, minIdle, ioThreads, workerThreads);

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);

        ClientOptions clientOptions = ClientOptions
                .builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT)
                .autoReconnect(true)
                .build();

        ClientResources res = DefaultClientResources
                .builder()
                .ioThreadPoolSize(ioThreads)
                .computationThreadPoolSize(workerThreads)
                .build();

        return LettucePoolingClientConfiguration
                .builder()
                .poolConfig(genericObjectPoolConfig)
                .clientOptions(clientOptions)
                .clientResources(res)
                .build();
    }

    @Bean(name = "customRedisTemplate")
    @Primary
    RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory(redisStandaloneConfiguration(), getLettucePoolConfig()));
        template.setDefaultSerializer(jackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
