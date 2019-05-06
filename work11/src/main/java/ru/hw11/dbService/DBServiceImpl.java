package ru.hw11.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.hw11.CacheEngine.CacheEngineImpl;
import ru.hw11.base.DBService;
import ru.hw11.base.cache.CacheEngine;
import ru.hw11.base.cache.MyElement;
import ru.hw11.base.dataSet.UserDataSet;
import ru.hw11.dbService.dao.UserDataSetDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, UserDataSet> cache;

    public DBServiceImpl() {
        this.sessionFactory = createSessionFactory();
        cache = new CacheEngineImpl<>(5, 5000, 0, false);
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
            cache.put(new MyElement<>(dataSet.getId(), dataSet));
            tx.commit();
        }

    }


    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            if (cache.get(id) == null) {
                UserDataSetDAO userDataSetDAO = new UserDataSetDAO(session);
                cache.put(new MyElement<>(id, userDataSetDAO.read(id)));
            }
            return cache.get(id).getValue();
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
                    new MyElement<>(
                            userDataSet.getId(),
                            userDataSet
                    )
            );
        }
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
        cache.dispose();
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
