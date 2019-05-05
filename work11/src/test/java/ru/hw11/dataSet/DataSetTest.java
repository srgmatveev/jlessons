package ru.hw11.dataSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw11.base.dataSet.DataSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

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