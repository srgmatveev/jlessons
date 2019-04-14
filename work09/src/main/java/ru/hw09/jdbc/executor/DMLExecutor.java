package ru.hw09.jdbc.executor;

import ru.hw09.jdbc.handler.ResultHandler;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class DMLExecutor extends DDLExecutor {
    private final Connection connection;
    public DMLExecutor(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    public CachedRowSet execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            System.out.println(query);
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
}
