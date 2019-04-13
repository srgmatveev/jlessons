package ru.hw09.jdbc.dbservice;

import ru.hw09.jdbc.connection.ConnectionHelper;
import ru.hw09.jdbc.data.DataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DBServiceConnection implements DBService {
    private final Connection connection;

    public DBServiceConnection(ConnectionHelper connectionHelper) {
        connection = connectionHelper.getConnection();
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void deleteTables(String... tables) throws SQLException {

    }

    @Override
    public void createTables(String... tables) throws SQLException {

    }

    @Override
    public <T extends DataSet> void save(T data) throws SQLException, NoSuchMethodException {

    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends DataSet> Collection<T> loadAll() {
        return new ArrayList<>();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    protected Connection getConnection() {
        return connection;
    }
}
