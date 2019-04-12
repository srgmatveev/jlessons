package ru.hw09.jdbc.dbservice;

import ru.hw09.jdbc.connection.ConnectionHelper;
import ru.hw09.jdbc.executor.DDLExecutor;
import ru.hw09.jdbc.executor.Executor;
import ru.hw09.jdbc.utils.ResBundle;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBServiceDropCreateTables extends DBServiceConnection {


    public DBServiceDropCreateTables(ConnectionHelper connectionHelper) {
        super(connectionHelper);
    }

    @Override
    public void deleteTables(String... tables) throws SQLException {
        Executor executor = new DDLExecutor(super.getConnection());
        for(String table : tables){
            StringBuilder stringBuilder = new StringBuilder("DROP TABLE IF EXISTS ");
            stringBuilder.append(table);
            executor.execUpdate(stringBuilder.toString());
        }
    }

    @Override
    public void createTables(String... tables) throws SQLException {
        Executor executor = new DDLExecutor(super.getConnection());
        for(String table : tables){
            createTable(table);
        }
    }

    private void createTable(String table){
        ResourceBundle rb = ResBundle.getBundle(new StringBuilder(table.toLowerCase()).append("_table").toString());

    }

}
