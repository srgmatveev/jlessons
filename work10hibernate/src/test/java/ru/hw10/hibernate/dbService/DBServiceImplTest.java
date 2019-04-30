package ru.hw10.hibernate.dbService;

import org.junit.jupiter.api.Test;
import ru.hw10.hibernate.base.DBService;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceImplTest {

    @Test
    void getLocalStatus() {
        DBService dbService = new DBServiceImpl();
        assertEquals(dbService.getLocalStatus(),"ACTIVE");
    }
}