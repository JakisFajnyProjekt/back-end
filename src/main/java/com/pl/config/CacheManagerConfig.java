package com.pl.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CacheManagerConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache("addressesList"));
        caches.add(new ConcurrentMapCache("orderList"));
        caches.add(new ConcurrentMapCache("dishesList"));
        caches.add(new ConcurrentMapCache("restaurantsList"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Scheduled(fixedRate = 3600000)
    public void evictAllCachesAtIntervals() {
        cacheManager().getCacheNames().forEach(cacheName -> cacheManager().getCache(cacheName).clear());
    }

    @Bean
    @Profile("dev")
    public CacheManager getNoOpCacheManager() {
        return new NoOpCacheManager();
    }
}
