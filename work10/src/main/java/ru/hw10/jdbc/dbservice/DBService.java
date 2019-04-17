package ru.hw10.jdbc.dbservice;

import ru.hw10.jdbc.data.DataSet;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void deleteTables(String... tables) throws SQLException;

    void createTables(String... tables) throws SQLException;

    <T extends DataSet> void save(T data) throws SQLException, NoSuchMethodException;
    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
    <T extends DataSet> Collection<T> loadAll();

}
