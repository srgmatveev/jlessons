package ru.hw11.utils;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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