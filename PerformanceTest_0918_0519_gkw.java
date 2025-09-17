// 代码生成时间: 2025-09-18 05:19:21
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Properties;
import java.util.Random;

public class PerformanceTest {

    private static final int WARMUP_RUNS = 100;
    private static final int MEASUREMENT_RUNS = 1000;
    private static final int ELEMENTS_TO_CREATE = 1000;

    private static SessionFactory sessionFactory;

    static {
        try {
            // 初始化Hibernate SessionFactory
            Properties properties = new Properties();
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/your_database");
            properties.setProperty("hibernate.connection.username", "your_username");
            properties.setProperty("hibernate.connection.password", "your_password");
            properties.setProperty("hibernate.hbm2ddl.auto", "update");
            properties.setProperty("hibernate.show_sql", "true");
            sessionFactory = new Configuration().configure().addProperties(properties).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting performance test...");
        warmUp();
        long startTime = System.currentTimeMillis();
        createEntities(ELEMENTS_TO_CREATE);
        long endTime = System.currentTimeMillis();
        System.out.println("Performance test completed in: " + (endTime - startTime) + " ms");
    }

    private static void warmUp() {
        // 预热，确保JVM优化
        for (int i = 0; i < WARMUP_RUNS; i++) {
            createEntity();
            deleteEntity();
        }
    }

    private static void createEntities(int count) {
        for (int i = 0; i < count; i++) {
            createEntity();
        }
    }

    private static void createEntity() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // 创建一个新的实体
            MyEntity entity = new MyEntity(generateRandomName());
            session.save(entity);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    private static void deleteEntity() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // 删除一个实体
            MyEntity entity = session.get(MyEntity.class, new Random().nextInt());
            session.delete(entity);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    private static String generateRandomName() {
        Random random = new Random();
        return "Name" + random.nextInt();
    }

    // 假设的实体类
    public static class MyEntity {
        private int id;
        private String name;

        public MyEntity(String name) {
            this.name = name;
        }

        // getters and setters
    }
}
