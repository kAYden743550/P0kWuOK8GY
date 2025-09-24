// 代码生成时间: 2025-09-24 11:34:59
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.Properties;

/**
 * DatabaseConnectionPoolManager is responsible for managing the Hibernate connection pool.
 * It provides methods to start and stop the connection pool, as well as to acquire and release connections.
 */
public class DatabaseConnectionPoolManager {

    private static SessionFactory sessionFactory;

    /**
     * Initializes the Hibernate SessionFactory.
     * This method should be called once to set up the connection pool.
     * @param configFilePath The path to the Hibernate configuration file.
     * @throws HibernateException If there is an error during the initialization.
     */
    public static void initialize(String configFilePath) throws HibernateException {
        try {
            Configuration configuration = new Configuration().configure(configFilePath);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new HibernateException("Initial SessionFactory creation failed.", ex);
        }
    }

    /**
     * Closes the Hibernate SessionFactory, effectively shutting down the connection pool.
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     * Acquires a new session from the connection pool.
     * @return A Hibernate Session object.
     */
    public static Session acquireSession() {
        return sessionFactory.openSession();
    }

    /**
     * Releases a session back to the connection pool.
     * @param session The session to be released.
     */
    public static void releaseSession(Session session) {
        if (session != null) {
            session.close();
        }
    }
}
