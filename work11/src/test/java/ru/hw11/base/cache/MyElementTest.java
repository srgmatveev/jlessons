package ru.hw11.base.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyElement.class)
public class MyElementTest {
    private MyElement<Long, String> myElement;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(12345l);
        myElement = new MyElement<Long, String>(123l, "Sergio");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCurrentTime() {
        assertEquals(myElement.getCurrentTime(), 12345l);
    }

    @Test
    public void getKey() {
        assertEquals(myElement.getKey().toString(), "123");
    }

    @Test
    public void getValue() {
        assertEquals(myElement.getValue(), "Sergio");
    }

    @Test
    public void getCreationTime() {
        assertEquals(myElement.getCreationTime(),12345l);
    }

    @Test
    public void getLastAccessTime() {
        assertEquals(myElement.getLastAccessTime(),12345l);
    }

    @Test
    public void setAccessed() {
        myElement.setAccessed();
        assertEquals(myElement.getLastAccessTime(),12345l);
    }
}