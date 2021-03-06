package ru.hw09.jdbc.main;

import ru.hw09.jdbc.connection.MySQLConnectionHelper;
import ru.hw09.jdbc.data.DataSet;
import ru.hw09.jdbc.data.UsersDataSet;
import ru.hw09.jdbc.dbservice.DBService;
import ru.hw09.jdbc.dbservice.DBServiceAllOperations;

public class DemoMain {


    public static void main(String... args) throws Exception {
        new DemoMain().initBase().run();
    }

        private void run() throws Exception {

        }

    private DemoMain initBase() throws Exception{
        System.out.println("Database initialization...");
        try (DBService dbService = new DBServiceAllOperations(new MySQLConnectionHelper())) {
            String[] tables = new String[]{"User"};
            dbService.deleteTables(tables);
            dbService.createTables(tables);

            DataSet dataSet1 = new UsersDataSet("Sergio",35);
            dbService.save(dataSet1);
            DataSet dataSet2 = new UsersDataSet("Peter", 46);
            dbService.save(dataSet2);

            dbService.load(dataSet1.getId(), UsersDataSet.class);
            dbService.load(dataSet2.getId(), UsersDataSet.class);
        }


        return this;
    }

}
