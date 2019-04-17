package ru.hw10.jdbc.executor;

import ru.hw10.jdbc.handler.ResultHandler;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface Executor {
    CachedRowSet execQuery(String query, ResultHandler handler) throws SQLException;
    Map<Integer, ResultSet> execUpdate(String update) throws SQLException;

}
