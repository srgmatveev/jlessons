package ru.hw10.jdbc.dbservice;

import ru.hw10.jdbc.connection.ConnectionHelper;
import ru.hw10.jdbc.executor.DDLExecutor;
import ru.hw10.jdbc.executor.Executor;
import ru.hw10.jdbc.utils.ResBundle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
            System.out.println(String.format("Drop table '%s'", table));
            executor.execUpdate(stringBuilder.toString());
        }
    }

    @Override
    public void createTables(String... tables) throws SQLException {
        Executor executor = new DDLExecutor(super.getConnection());
        Map<String,Set<String>> foreignKeys = new HashMap<>();
        for(String table : tables){
           mergeMaps(foreignKeys,createTable(table, executor));
        }
    }

    private <T,V> void mergeMaps(Map<T,V> to, Map<T,V> from){
        for(Map.Entry<T,V> entry : from.entrySet()){
            to.put(entry.getKey(),entry.getValue());
        }
    }

    private  Map<String,Set<String>> createTable(String table, Executor executor) throws SQLException {
        if (table.isEmpty()) return null;
        Map<String, Set<String>> foreignKeys = new HashMap<>();
        ResourceBundle rb = ResBundle.getBundle(new StringBuilder(table).append("_table").toString());
        StringBuilder stringBuilder = new StringBuilder(String.format("CREATE TABLE IF NOT EXISTS %s ", table));
        stringBuilder.append(ResBundle.readAllFile(rb));
        if (haveFString(stringBuilder.toString())){
            foreignKeys = ResBundle.readAllForeignKeys(table,rb);
        }

        String buildString = clearTableFromFK(stringBuilder.toString(), foreignKeys);
        System.out.println(String.format("Create table '%s'", table));
        System.out.println(String.format("%s", buildString));
        executor.execUpdate(buildString);
        return foreignKeys;
    }

    private String clearTableFromFK(String str, Map<String, Set<String>> fk) {

        for(Map.Entry<String, Set<String>> entry : fk.entrySet()){
            for(String item : entry.getValue()){
                str = str.replace(item,"");
            }

        }

     str = str.replaceAll("\\,\\s*\\,","\\,");
     str = str.replaceAll("\\,\\s*\\);","\\,");
     return  str;
    }

    private boolean haveFString(String str){
        if (str == null || str.isEmpty()) return false;
        if (str.toLowerCase().contains("foreign key"))
        {
            return true;
        }
        return false;
    }

}
