package ru.hw09.jdbc.executor;

import ru.hw09.jdbc.handler.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Executor {
    void execQuery(String query, ResultHandler handler) throws SQLException;
    int execUpdate(String update) throws SQLException;

}
