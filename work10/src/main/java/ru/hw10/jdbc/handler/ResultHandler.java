package ru.hw10.jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultHandler {
    void handle(ResultSet result) throws SQLException;
}
