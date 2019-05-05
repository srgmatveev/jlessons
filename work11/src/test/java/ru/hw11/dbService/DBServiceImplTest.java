package ru.hw11.dbService;

import org.junit.jupiter.api.Test;
import ru.hw11.base.DBService;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceImplTest {

    @Test
    void getLocalStatus() {
        DBService dbService = new DBServiceImpl();
        assertEquals(dbService.getLocalStatus(),"ACTIVE");
    }
}