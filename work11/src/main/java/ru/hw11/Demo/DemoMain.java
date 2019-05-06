package ru.hw11.Demo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hw11.base.DBService;
import ru.hw11.base.dataSet.AddressDataSet;
import ru.hw11.base.dataSet.PhoneDataSet;
import ru.hw11.base.dataSet.UserDataSet;
import ru.hw11.dbService.DBServiceImpl;
import ru.hw11.utils.SessionUtil;

import java.util.List;

public class DemoMain {

    public static void main(String[] args) {

        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);




            PhoneDataSet phoneDataSet = new PhoneDataSet("1234");
            PhoneDataSet phoneDataSet1 = new PhoneDataSet("9111");

            AddressDataSet addressDataSet = new AddressDataSet();
            addressDataSet.setStreet("Five avenue");

            try(Session session = SessionUtil.getSession()) {
                Transaction tx = session.beginTransaction();
                session.save(phoneDataSet);
                session.save(phoneDataSet1);
                session.save(addressDataSet);
                tx.commit();
            }

            UserDataSet userDataSet = new UserDataSet();
            userDataSet.setName("Sergio");
            userDataSet.addAddress(addressDataSet);
            userDataSet.addPhoneNumber(phoneDataSet);
            userDataSet.addPhoneNumber(phoneDataSet1);
            dbService.save(userDataSet);

            UserDataSet sergio = dbService.readByName("Sergio");
            System.out.println("User by Name: " + sergio);

            Long user_id = sergio.getId();
            UserDataSet user = dbService.read(user_id);
            System.out.println("User by ID: " + user);

            List<UserDataSet> users = dbService.readAll();
            for(UserDataSet item : users)
                System.out.println(item);

        dbService.shutdown();

    }
}
