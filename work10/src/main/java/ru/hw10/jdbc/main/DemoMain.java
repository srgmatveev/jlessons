package ru.hw10.jdbc.main;

import ru.hw10.jdbc.connection.MySQLConnectionHelper;
import ru.hw10.jdbc.data.AddressDataSet;
import ru.hw10.jdbc.data.DataSet;
import ru.hw10.jdbc.data.PhoneDataSet;
import ru.hw10.jdbc.data.UsersDataSet;
import ru.hw10.jdbc.dbservice.DBService;
import ru.hw10.jdbc.dbservice.DBServiceAllOperations;

public class DemoMain {


    public static void main(String... args) throws Exception {
        new DemoMain().initBase().run();
    }

        private void run() throws Exception {

        }

    private DemoMain initBase() throws Exception{
        System.out.println("Database initialization...");
        try (DBService dbService = new DBServiceAllOperations(new MySQLConnectionHelper())) {
            String[] tables_create = new String[]{"User"};
            String[] tables_delete = new String[]{"User","Address","Phone"};
            dbService.deleteTables(tables_delete);
            dbService.createTables(tables_create);


            DataSet phone1 = new PhoneDataSet("911000201");
            dbService.save(phone1);
            DataSet phone2 = new PhoneDataSet("8800000555");
            dbService.save(phone2);
            DataSet phone3 = new PhoneDataSet("8926756871");
            dbService.save(phone3);

            dbService.load(phone3.getId(), PhoneDataSet.class);

            DataSet address1 = new AddressDataSet("Pigalue");
            dbService.save(address1);
            DataSet address2 = new AddressDataSet("Dante rue");
            dbService.save(address2);

            DataSet dataSet1 = new UsersDataSet("Sergio",35,  (AddressDataSet) address1, (PhoneDataSet) phone1);
            dbService.save(dataSet1);

           DataSet dataSet2 = new UsersDataSet("Peter", 46,  (AddressDataSet) address2, (PhoneDataSet) phone3);
           dbService.save(dataSet2);

           dbService.load(dataSet1.getId(), UsersDataSet.class);
           dbService.load(dataSet2.getId(), UsersDataSet.class);
        }


        return this;
    }

}
