package ru.hw09.jdbc.handler;

import java.sql.ResultSet;

public interface ResultHandler {
    void handle(ResultSet result);
}
