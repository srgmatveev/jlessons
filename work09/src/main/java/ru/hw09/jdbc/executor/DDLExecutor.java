package ru.hw09.jdbc.executor;

import ru.hw09.jdbc.handler.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DDLExecutor implements Executor {
    private final Connection connection;

    public DDLExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void execQuery(String query, ResultHandler handler) throws SQLException {

    }

    @Override
    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }
}
