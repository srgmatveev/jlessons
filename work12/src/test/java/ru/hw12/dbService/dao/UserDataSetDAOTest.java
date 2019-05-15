package ru.hw12.dbService.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw12.base.dataSet.UserDataSet;
import ru.hw12.utils.SessionUtil;

import static org.junit.jupiter.api.Assertions.*;

class UserDataSetDAOTest {

    private UserDataSetDAO userDataSetDAO;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        session = SessionUtil.getSession();
        transaction = session.beginTransaction();
        userDataSetDAO = new UserDataSetDAO(session);
    }

    @AfterEach
    void tearDown() {
        if (transaction.isActive()) {
            transaction.commit();
        }
        if (session.isOpen()) {
            session.close();
        }
    }

    @Test
    void save() {
        Long id;
        UserDataSet userDataSet = new UserDataSet();
        userDataSetDAO.save(userDataSet);
        id = userDataSet.getId();
        assertNotNull(id);
        session.remove(userDataSet);
    }

    @Test
    void read() {

        Long id;
        String name = "Sergio";
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName(name);
        userDataSetDAO.save(userDataSet);
        id = userDataSet.getId();
        assertNotEquals(id, 0);

        UserDataSet user = userDataSetDAO.read(id);
        assertEquals(user.getName(),name);
        session.remove(userDataSet);

    }

    @Test
    void readByName() {

        Long id;
        String name = "Sergio";
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName(name);
        userDataSetDAO.save(userDataSet);
        id = userDataSet.getId();
        assertNotNull(id);
        UserDataSet user = userDataSetDAO.readByName(name);
        assertEquals(user.getName(),name);
        session.remove(userDataSet);
    }

    @Test
    void readAll() {

        String name = "Sergio";
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName(name);
        userDataSetDAO.save(userDataSet);

        String name1 = "Sergio1";
        UserDataSet userDataSet1 = new UserDataSet();
        userDataSet1.setName(name1);
        userDataSetDAO.save(userDataSet1);

        String name2 = "Sergio2";
        UserDataSet userDataSet2 = new UserDataSet();
        userDataSet2.setName(name2);
        userDataSetDAO.save(userDataSet2);

        assertEquals(userDataSetDAO.readAll().size(),3);
        session.remove(userDataSet);
        session.remove(userDataSet1);
        session.remove(userDataSet2);

    }
}