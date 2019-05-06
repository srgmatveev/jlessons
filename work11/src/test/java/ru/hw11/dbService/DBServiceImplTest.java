package ru.hw11.dbService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw11.base.DBService;
import ru.hw11.base.dataSet.UserDataSet;

import static org.junit.jupiter.api.Assertions.*;

class DBServiceImplTest {
     private  DBService dbService;
    @BeforeEach
    void setUp()
    {
        dbService = new DBServiceImpl();
    }

    @Test
    void getLocalStatus() {

        assertEquals(dbService.getLocalStatus(),"ACTIVE");
    }

    @Test
    public void save() {

        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName("Petruccho");
        dbService.save(userDataSet);
    }
}