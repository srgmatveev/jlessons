package ru.hw10.jdbc.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw10.jdbc.data.AddressDataSetTest;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TableUtilsTest {

    @Test
    void getTableNameFromFK() {
        String input = "FOREIGN KEY(phone_id) REFERENCES Phone(id)";
        String regexp = "REFERENCES\\s*(\\w*)\\s*\\(";
        Optional<String> optional = TableUtils.getTableNameFromFK(input, regexp);
        assertEquals(optional.get(), "Phone");

        input = "FoReign kEY(phone_id) rEfEReNCES Phone(id)";
        optional = TableUtils.getTableNameFromFK(input, regexp);
        assertEquals(optional.get(), "Phone");

        input = "FOREIGN KEY(phone_id) REFERENCES (id)";
        optional = TableUtils.getTableNameFromFK(input, regexp);
        assertEquals(optional.get(), "");
    }

    @Test
    void getAddonsCreateTables()  {
        Optional<Set<String>> optionalStrings = TableUtils.getAddonsCreateTables("User");
        assertTrue(optionalStrings.get().toString().contains("Address"));
        assertTrue(optionalStrings.get().toString().contains("AddressDataSetTest"));
    }

    @Test
    void getId() {

    String tmp = "AddressDataSet{id='1'street='Pigalue'}";
    assertEquals(TableUtils.getId(tmp),"1");

    }

    @Test
    void returnObject() {
       String tmpStr = "ru.hw10.jdbc.data.PhoneDataSet1";
       assertEquals(TableUtils.returnObject(tmpStr).getClass().getName(),tmpStr);
    }
}