package ru.hw09.jdbc.main;

import ru.hw09.jdbc.connection.MySQLConnectionHelper;
import ru.hw09.jdbc.dbservice.DBService;
import ru.hw09.jdbc.dbservice.DBServiceDropCreateTables;

public class DemoMain {
    public static void main(String... args) throws Exception {
        new DemoMain().initBase().run();
    }

        private void run() throws Exception {

        }

    private DemoMain initBase() throws Exception{
        System.out.println("Database initialization...");
        try (DBService dbService = new DBServiceDropCreateTables(new MySQLConnectionHelper())) {
            String[] tables = new String[]{"User"};
            dbService.deleteTables(tables);
            dbService.createTables(tables);
        }
        return this;
    }

}
