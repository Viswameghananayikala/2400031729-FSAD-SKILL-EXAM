package com.klef.fsad.exam;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtil
 * Provides a singleton SessionFactory built from hibernate.cfg.xml.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    // Private constructor – utility class
    private HibernateUtil() {}

    /** Returns the application-wide SessionFactory, creating it on first call. */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")   // loads from classpath root
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    /** Closes the SessionFactory gracefully (call on application shutdown). */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory closed.");
        }
    }
}
