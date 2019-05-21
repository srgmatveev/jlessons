package ru.hw12.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hw12.base.dataSet.UserDataSet;
import ru.hw12.dbService.dao.UserDataSetDAO;

import javax.cache.Cache;

import static org.junit.jupiter.api.Assertions.*;

class EnCacheUtilTest {
    private EhCacheUtil ehCacheUtil;
    private UserDataSetDAO userDataSetDAO;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        session = SessionUtil.getSession();
        transaction = session.beginTransaction();
        userDataSetDAO = new UserDataSetDAO(session);
        ehCacheUtil = new EhCacheUtil();
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
    void getDataSetCache() {
        Cache<Long, UserDataSet> cache = ehCacheUtil.getDataSetCache("userDataSetCacheTest", Long.class, UserDataSet.class);
        Long id;
        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName("Fufel");
        userDataSetDAO.save(userDataSet);
        id = userDataSet.getId();
        assertNotNull(id);
        cache.clear();
        cache.put(id, userDataSet);
        assertTrue(cache.containsKey(id));
        assertEquals(cache.get(id).getName(),"Fufel");
        session.remove(userDataSet);
    }
}
