package ru.hw10.hibernate.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.hw10.hibernate.base.DBService;
import ru.hw10.hibernate.base.dataSet.UserDataSet;
import ru.hw10.hibernate.dbService.dao.UserDataSetDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceImpl() {
        this.sessionFactory = createSessionFactory();
    }

    private static SessionFactory createSessionFactory() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        return new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            userDataSetDAO.save(dataSet);
            tx.commit();
        }
    }

    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            return userDataSetDAO.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            return userDataSetDAO.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            return userDataSetDAO.readAll();
        });
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
