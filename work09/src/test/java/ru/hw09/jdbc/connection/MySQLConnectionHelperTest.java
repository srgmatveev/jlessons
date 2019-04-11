package ru.hw09.jdbc.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class MySQLConnectionHelperTest {
    private ConnectionHelper connectionHelper;
    @BeforeEach
    void setUp() {
        connectionHelper = new MySQLConnectionHelper("db_test");
    }


    @Test
    void buildUrl() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connectionHelper.getClass().getDeclaredMethod("buildUrl");
        method.setAccessible(true);
        assertEquals(method.getName(),"buildUrl");

        String url = (String) method.invoke(connectionHelper);
        assertEquals(url,"");

    }


    @Test
    void resourcesBundle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connectionHelper.getClass().getDeclaredMethod("getBundle");
        method.setAccessible(true);
        assertEquals(method.getName(),"getBundle");
        ResourceBundle rb = (ResourceBundle) method.invoke(connectionHelper);
        assertNotNull(rb);
        System.out.println(rb.getString("url"));

    }

}