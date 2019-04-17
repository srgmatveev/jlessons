package ru.hw10.jdbc.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.hw10.jdbc.utils.ResBundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MySQLConnectionHelperTest {
    private ConnectionHelper connectionHelper, connectionHelper1;

    @BeforeEach
    void setUp() {
        connectionHelper = new MySQLConnectionHelper("db_test");
        connectionHelper1 = new MySQLConnectionHelper("db_test_fail");
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
        ResourceBundle rb = ResBundle.getBundle("db_test");
        assertNotNull(rb);
        assertEquals(rb.getString("url"), "jdbc:mysql://");

    }

    @Test
    void getParamPropertiesFile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ResourceBundle rb = ResBundle.getBundle("db_test");
        Optional<String> name = ResBundle.getParamPropertiesFile(rb,"url");
        assertEquals(name.get(),"jdbc:mysql://");
        name = ResBundle.getParamPropertiesFile( rb, "test_url");
        assertFalse(name.isPresent());
        name = ResBundle.getParamPropertiesFile( rb, "eees");
        assertFalse(name.isPresent());
     }

    @Test
    void getConnection() throws SQLException {
        assertNotNull(connectionHelper.getConnection());
    }

    @Test
    void getConnectionFail()  {

       assertThrows(java.lang.RuntimeException.class, ()-> connectionHelper1.getConnection());
    }
}