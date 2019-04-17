package ru.hw10.jdbc.executor;

import ru.hw10.jdbc.handler.ResultHandler;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DDLExecutor implements Executor {
    private final Connection connection;

    public DDLExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CachedRowSet execQuery(String query, ResultHandler handler) throws SQLException {
        return null;
    }

    @Override
    public Map<Integer, ResultSet> execUpdate(String update) throws SQLException {
        Map<Integer,ResultSet> map = new HashMap<>();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(update,Statement.RETURN_GENERATED_KEYS);
            map.put(stmt.getUpdateCount(), stmt.getGeneratedKeys());
            ResultSet keys = stmt.getGeneratedKeys();
            return map;
        }
    }


}
