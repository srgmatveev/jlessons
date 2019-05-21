package ru.hw12.utils;

import ru.hw12.base.dataSet.UserDataSet;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;
import java.util.Optional;

public class EhCacheUtil {
    private final static String XML_CONFIG = "/ehcache.xml";
    private final CachingProvider provider;
    private final CacheManager cacheManager;

    public EhCacheUtil() {
        provider = getProvider();
        cacheManager = initCacheManager();
        //MutableConfiguration<Long, UserDataSet> mutableConfiguration = new MutableConfiguration<>();
    }


    private CachingProvider getProvider() {
        return Caching.getCachingProvider();
    }

    private CacheManager initCacheManager() {
        CacheManager cacheManager = null;
        try {
            cacheManager = provider.getCacheManager(getClass().getResource(XML_CONFIG).toURI(),
                    getClass().getClassLoader());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        return cacheManager;
    }

    public <K, V> Cache<K, V> getDataSetCache(final String DataSetCacheName, Class<K> classK, Class<V> classV) {
        return (Cache<K, V>) this.cacheManager.getCache(DataSetCacheName, classK, classV);
    }

}
