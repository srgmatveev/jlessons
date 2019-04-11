package ru.hw09.jdbc.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MySQLConnectionHelperTest {
    private ConnectionHelper connectionHelper;
    @BeforeEach
    void setUp() {
        connectionHelper = new MySQLConnectionHelper();
    }


    @Test
    void buildUrl() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connectionHelper.getClass().getDeclaredMethod("buildUrl");
        method.setAccessible(true);
        assertEquals(method.getName(),"buildUrl");

        String url = (String) method.invoke(connectionHelper);
        assertEquals(url,"");
    }
}