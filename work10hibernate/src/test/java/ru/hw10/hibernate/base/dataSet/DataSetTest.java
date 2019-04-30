package ru.hw10.hibernate.base.dataSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataSetTest {
    private DataSet dataSet;

    @BeforeEach
    void setUp() {
        dataSet = new DataSet();
    }


    @Test
    void testId(){
        dataSet.setId((long)5);
        assertEquals(dataSet.getId(),5 );
    }


}