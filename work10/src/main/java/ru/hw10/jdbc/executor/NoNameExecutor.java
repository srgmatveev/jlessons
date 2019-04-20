package ru.hw10.jdbc.executor;

import ru.hw10.jdbc.handler.ResultHandler;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class NoNameExecutor implements Executor {
    private final Connection connection;

    public NoNameExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CachedRowSet execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(result);
            CachedRowSet rowSet1 = rowSet.createCopySchema();
            handler.handle(rowSet);
            return  rowSet1;
        }
    }

    @Override
    public Map<Integer, ResultSet> execUpdate(String update) throws SQLException {
        return null;
    }
}
