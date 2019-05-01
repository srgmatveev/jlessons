package ru.hw10.hibernate.Demo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hw10.hibernate.base.DBService;
import ru.hw10.hibernate.base.dataSet.AddressDataSet;
import ru.hw10.hibernate.base.dataSet.PhoneDataSet;
import ru.hw10.hibernate.base.dataSet.UserDataSet;
import ru.hw10.hibernate.dbService.DBServiceImpl;
import ru.hw10.hibernate.utils.SessionUtil;

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
            System.out.println(sergio.getPhoneNumbers().size());

            Long user_id = sergio.getId();
            UserDataSet user = dbService.read(user_id);
            System.out.println("User by ID: " + user);

            List<UserDataSet> users = dbService.readAll();
            for(UserDataSet item : users)
                System.out.println(item);


    }
}
