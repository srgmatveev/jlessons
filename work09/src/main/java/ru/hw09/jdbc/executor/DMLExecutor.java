package ru.hw09.jdbc.executor;

import ru.hw09.jdbc.handler.ResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DMLExecutor extends DDLExecutor {
    private final Connection connection;
    public DMLExecutor(Connection connection, Connection connection1) {
        super(connection);
        this.connection = connection1;
    }

    @Override
    public void execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            handler.handle(result);
        }
    }
}
