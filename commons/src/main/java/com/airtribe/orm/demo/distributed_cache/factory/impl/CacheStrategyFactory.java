package com.airtribe.orm.demo.distributed_cache.factory.impl;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.airtribe.orm.demo.distributed_cache.enums.Provider;
import com.airtribe.orm.demo.distributed_cache.factory.CacheStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheStrategyFactory {

  private Map<Provider, CacheStrategy> strategies;

  @Autowired
  public CacheStrategyFactory(Set<CacheStrategy> strategySet) {
    createStrategy(strategySet);
  }

  public CacheStrategy findStrategy(Provider sftpStrategyName) {
    return strategies.get(sftpStrategyName);
  }

  private void createStrategy(Set<CacheStrategy> strategySet) {
    strategies = new EnumMap<>(Provider.class);
    strategySet.forEach(strategy -> strategies.put(strategy.getStrategyType(), strategy));
    log.info("new cache Strategy created: {} ", strategies);
  }

}
