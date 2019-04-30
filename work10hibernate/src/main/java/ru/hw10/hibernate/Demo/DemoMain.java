package ru.hw10.hibernate.Demo;

import ru.hw10.hibernate.base.DBService;
import ru.hw10.hibernate.base.dataSet.PhoneDataSet;
import ru.hw10.hibernate.base.dataSet.UserDataSet;
import ru.hw10.hibernate.dbService.DBServiceImpl;

import java.util.List;

public class DemoMain {

    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        PhoneDataSet phoneDataSet = new PhoneDataSet("1234");
        PhoneDataSet phoneDataSet1 = new PhoneDataSet("9111");
        UserDataSet userDataSet = new UserDataSet();


    }
}
