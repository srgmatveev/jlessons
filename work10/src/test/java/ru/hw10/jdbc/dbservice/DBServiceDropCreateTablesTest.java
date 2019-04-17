package ru.hw10.jdbc.dbservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.hw10.jdbc.connection.ConnectionHelper;
import ru.hw10.jdbc.connection.MySQLConnectionHelper;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceDropCreateTablesTest {
    private ConnectionHelper connectionHelper;
    @BeforeEach
    void setUp() {
        connectionHelper = new MySQLConnectionHelper("db_test");
    }

    @AfterEach
    void tearDown() {
        connectionHelper = null;
    }

    @Test
    void deleteTables() {
    }


    @Test
    void createTables() throws Exception {
        try (DBService dbService = new DBServiceDropCreateTables(connectionHelper)){
            String[] tables = {"User"};
            dbService.createTables(tables);
        }

    }
}