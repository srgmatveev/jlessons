package ru.hw09.jdbc.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Enumeration;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ResBundleTest {
    private ResourceBundle resourceBundle,resourceBundle1;
    @BeforeEach
    void setUp(){
        resourceBundle = ResourceBundle.getBundle("User_table");
        resourceBundle1 = ResourceBundle.getBundle("Users_table");
    }

    @Test
    void readAllFile() {
        String tmpString = "( name VARCHAR(255) , id BIGINT(20) NOT NULL AUTO_INCREMENT , CONSTRAINT users_pk PRIMARY KEY (id) , age INT(3));";
        assertEquals(ResBundle.readAllFile(resourceBundle), tmpString);
        assertEquals(ResBundle.readAllFile(resourceBundle1),"();");
    }
}