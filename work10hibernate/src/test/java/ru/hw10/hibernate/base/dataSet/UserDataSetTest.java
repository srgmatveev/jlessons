package ru.hw10.hibernate.base.dataSet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw10.hibernate.utils.SessionUtil;

import static org.junit.jupiter.api.Assertions.*;

class UserDataSetTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getName() {
        String name = "Sergio";
        UserDataSet userDataSet = new UserDataSet();
        try (Session session = SessionUtil.getSession()) {

            Transaction tx = session.beginTransaction();
            userDataSet.setName(name);
            session.save(userDataSet);
            tx.commit();
        }

        assertEquals(userDataSet.getName(),name);
    }

    @Test
    void getAge() {
        int defAge=15;
        UserDataSet userDataSet = new UserDataSet();
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            userDataSet.setAge(defAge);
            session.save(userDataSet);
            tx.commit();
        }
        assertEquals(userDataSet.getAge(),defAge);
    }
}