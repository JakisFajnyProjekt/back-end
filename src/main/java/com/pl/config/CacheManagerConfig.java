package com.pl.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Configuration
@EnableScheduling
public class CacheManagerConfig {

    @Bean
    public Cache cacheAddress() {
        return new ConcurrentMapCache("addressesList");
    }

    @Bean
    public Cache cacheDish() {
        return new ConcurrentMapCache("dishesList");
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(cacheAddress(), cacheDish()));
        return cacheManager;
    }

    @Scheduled(fixedRate = 60000)
    public void evictAllCachesAtIntervals() {
        cacheManager().getCacheNames().forEach(cacheName -> cacheManager().getCache(cacheName).clear());
    }

    @Bean
    @Profile("dev")
    public CacheManager getNoOpCacheManager() {
        return new NoOpCacheManager();
    }
}
