package ru.hw12.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jboss.logging.Logger;

public class SessionUtil {
    private static final SessionUtil instance = new SessionUtil();
    private final SessionFactory factory;
    private static final String CONFIG_NAME = "/configuration.properties";
    Logger logger = Logger.getLogger(this.getClass());

    public SessionUtil() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        this.factory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }

    public static SessionUtil getInstance() {
        return instance;
    }

    public static Session getSession(){
        return instance.factory.openSession();
    }
}
