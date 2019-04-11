package ru.hw09.jdbc.dbservice;

import ru.hw09.jdbc.data.DataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void deleteTables() throws SQLException;

    void createTables() throws SQLException;

    <T extends DataSet> void save(T user);
    <T extends DataSet> T load(long id, Class<T> clazz);
    <T extends DataSet> List<T> loadAll();

}
