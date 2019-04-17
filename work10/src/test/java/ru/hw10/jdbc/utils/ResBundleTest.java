package ru.hw10.jdbc.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;
import java.util.Map;
import java.util.Set;

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
        String tmpString = "( phone_id BIGINT(20) , FOREIGN KEY(phone_id) REFERENCES Phone(id) , name VARCHAR(255) , id BIGINT(20) NOT NULL AUTO_INCREMENT , CONSTRAINT PK_User PRIMARY KEY (id) , age INT(3));";
        assertEquals(ResBundle.readAllFile(resourceBundle), tmpString);
        assertEquals(ResBundle.readAllFile(resourceBundle1),"();");
    }

    @Test
    void readAllForeignKeys() {
        String table ="User";
        Map<String, Set<String>> map = ResBundle.readAllForeignKeys(table,resourceBundle);
        assertEquals(map.toString(),"{User=[FOREIGN KEY(phone_id) REFERENCES Phone(id)]}");
    }
}