package ru.hw09.jdbc.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(method.getName(), "buildUrl");

        String url = (String) method.invoke(connectionHelper);
        assertEquals(url, "jdbc:mysql://127.1.1:3310/hw09?user=dbuser&password=dbuserpassword&useSSL=false");

    }


    @Test
    void resourcesBundle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connectionHelper.getClass().getDeclaredMethod("getBundle");
        method.setAccessible(true);
        assertEquals(method.getName(), "getBundle");
        ResourceBundle rb = (ResourceBundle) method.invoke(connectionHelper);
        assertNotNull(rb);
        assertEquals(rb.getString("url"), "jdbc:mysql://");

    }

    @Test
    void getParamPropertiesFile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connectionHelper.getClass().getDeclaredMethod("getBundle");
        method.setAccessible(true);
        ResourceBundle rb = (ResourceBundle) method.invoke(connectionHelper);

        Class[] cArg = new Class[]{java.util.ResourceBundle.class, java.lang.String.class};
        method = connectionHelper.getClass().getDeclaredMethod("getParamPropertiesFile",cArg);
        method.setAccessible(true);
        assertEquals(method.getName(), "getParamPropertiesFile");

        Optional<String> name = (Optional<String>) method.invoke(connectionHelper, rb, "url");
        assertEquals(name.get(),"jdbc:mysql://");
        name = (Optional<String>) method.invoke(connectionHelper, rb, "test_url");
        assertFalse(name.isPresent());
        name = (Optional<String>) method.invoke(connectionHelper, rb, "eees");
        assertFalse(name.isPresent());
    }
}