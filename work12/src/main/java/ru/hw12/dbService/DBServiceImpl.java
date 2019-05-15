package ru.hw12.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.hw12.base.DBService;
import ru.hw12.base.dataSet.UserDataSet;
import ru.hw12.dbService.dao.UserDataSetDAO;

import javax.cache.Cache;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


public class DBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final Cache<Long, UserDataSet> cache;

    public DBServiceImpl(final Cache<Long, UserDataSet> cache) {
        this.sessionFactory = createSessionFactory();
        this.cache = Objects.requireNonNull(cache);
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
            cache.put(dataSet.getId(), dataSet);
            tx.commit();
        }

    }


    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            if (cache.get(id) == null) {
                UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
                cache.put(id, userDataSetDAO.read(id));
            }
            return cache.get(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            UserDataSet userDataSet = userDataSetDAO.readByName(name);
            addToCache(userDataSet);
            return userDataSet;
        });
    }

    @Override
    public List<UserDataSet> readAll() {
        List<UserDataSet> list = runInSession(session -> {
            UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
            return userDataSetDAO.readAll();
        });

        list.forEach(userDataSet -> {
            addToCache(userDataSet);
        });

        return list;
    }

    private void addToCache(UserDataSet userDataSet) {
        if (userDataSet != null) {
            cache.put(

                    userDataSet.getId(),
                    userDataSet
            );
        }
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
