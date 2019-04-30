package ru.hw10.hibernate.utils;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionUtilTest {

    @Test
    void getInstance() {
        assertNotNull(SessionUtil.getInstance());
    }

    @Test
    void getSession() {
        try (Session session = SessionUtil.getSession()) {
            assertNotNull(session);
        }
    }
}