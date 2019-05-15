package ru.hw12.dbService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw12.base.DBService;
import ru.hw12.base.dataSet.UserDataSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBServiceImplTest {
     private  DBService dbService;
    @BeforeEach
    void setUp()
    {
       // dbService = new DBServiceImpl();
    }

    @Test
    void getLocalStatus() {

      //  assertEquals(dbService.getLocalStatus(),"ACTIVE");
    }

    @Test
    public void save() {

        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName("Petruccho");
       // dbService.save(userDataSet);
    }
}