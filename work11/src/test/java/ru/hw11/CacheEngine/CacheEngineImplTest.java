package ru.hw11.CacheEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import ru.hw11.base.cache.CacheEngine;
import ru.hw11.base.cache.MyElement;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Timer;

import static org.junit.Assert.*;

public class CacheEngineImplTest {

    private CacheEngine<Long,String> cacheEngine;

    @Before
    public void setUp() throws Exception {
        cacheEngine = new CacheEngineImpl(5,5000,0,false);
    }


    public void put() {
     cacheEngine.put(new MyElement<Long, String>(1L,"Sergio"));

    }

    @Test
    public void get() {
     put();
     assertEquals(cacheEngine.get(1L).getValue(),"Sergio");
    }

    @Test
    public void getHitCount() throws NoSuchFieldException, IllegalAccessException {
        Field hit = cacheEngine.getClass().getDeclaredField("hit");
        hit.setAccessible(true);
        hit.setInt(cacheEngine,119);
        assertEquals(cacheEngine.getHitCount(),119);
    }

    @Test
    public void getMissCount() throws NoSuchFieldException, IllegalAccessException {
        Field miss = cacheEngine.getClass().getDeclaredField("miss");
        miss.setAccessible(true);
        miss.setInt(cacheEngine,2251);
        assertEquals(cacheEngine.getMissCount(),2251);
    }

}