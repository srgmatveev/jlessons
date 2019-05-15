package ru.hw12.dataSet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw12.base.dataSet.AddressDataSet;
import ru.hw12.base.dataSet.PhoneDataSet;
import ru.hw12.base.dataSet.UserDataSet;
import ru.hw12.utils.SessionUtil;

import static org.junit.jupiter.api.Assertions.*;

class UserDataSetTest {
    private UserDataSet userDataSet;

    @BeforeEach
    void setUp() {
        userDataSet = new UserDataSet();

    }


    @Test
    void getName() {
        String name = "Sergio";

        try (Session session = SessionUtil.getSession()) {

            Transaction tx = session.beginTransaction();
            userDataSet.setName(name);
            session.save(userDataSet);
            tx.commit();
        }

        assertEquals(userDataSet.getName(), name);


    }

    @Test
    void getAge() {
        int defAge = 15;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            userDataSet.setAge(defAge);
            session.save(userDataSet);
            tx.commit();
        }
        assertEquals(userDataSet.getAge(), defAge);
    }

    @Test
    void addAddress() {
        String streetName = "Third avenue";
        Long address_id, user_id;
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(streetName);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(addressDataSet);
            address_id = addressDataSet.getId();
            tx.commit();
        }


        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            userDataSet.addAddress(addressDataSet);
            session.save(userDataSet);
            user_id = userDataSet.getId();
            tx.commit();
        }
        assertNotNull(address_id);
        assertNotNull(user_id);


        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            UserDataSet user = session.get(UserDataSet.class, user_id);
            if (user != null) {
                System.out.println(user.getAddress().getId());
                //assertEquals(user.getAddress().getId(), address_id);
                //assertEquals(user.getAddress().getStreet(), streetName);
                //assertEquals(user.getAddress().getUser(), user);
            } else assertFalse(true);
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            if (addressDataSet != null) session.remove(addressDataSet);
            if (userDataSet != null) session.remove(userDataSet);
            tx.commit();
        }



    }

    @Test
    void addPhoneNumber() {
        Long phone_id, user_id;
        String phoneNumber = "123456";
        PhoneDataSet phoneDataSet = new PhoneDataSet(phoneNumber);
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(phoneDataSet);
            phone_id = phoneDataSet.getId();
            userDataSet.addPhoneNumber(phoneDataSet);
            session.save(userDataSet);
            user_id = userDataSet.getId();
            tx.commit();
        }

        assertNotNull(phone_id);
        assertNotNull(user_id);
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            UserDataSet user = session.get(UserDataSet.class, user_id);
            if (user != null) {
                assertEquals(user.getPhoneNumbers().size(), 1);
                assertEquals(user.getPhoneNumbers().get(0).getUser(), user);
                assertEquals(user.getPhoneNumbers().get(0).getNumber(), phoneNumber);
            } else assertFalse(true);
            tx.commit();
        }


        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            if (phoneDataSet != null) session.remove(phoneDataSet);
            if (userDataSet != null) session.remove(userDataSet);
            tx.commit();
        }
    }
}