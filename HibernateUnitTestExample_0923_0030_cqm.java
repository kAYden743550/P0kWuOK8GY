// 代码生成时间: 2025-09-23 00:30:23
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// 单元测试类，使用JUnit 5进行单元测试
public class HibernateUnitTestExample {

    // 定义SessionFactory和Session
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;

    // 测试前的准备
    @BeforeEach
    public void setUp() {
        // 构建SessionFactory
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        // 开启Session
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    // 测试后清理
    @AfterEach
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            StandardServiceRegistryBuilder.destroy(sessionFactory.getSessionFactory());
        }
    }

    // 单元测试方法
    @Test
    public void testSaveAndRetrieveEntity() {
        // 创建一个新的实体对象
        MyEntity entity = new MyEntity();
        entity.setProperty("Test Property");

        // 保存实体到数据库
        session.save(entity);

        // 提交事务
        transaction.commit();
        transaction = session.beginTransaction();

        // 从数据库检索实体
        MyEntity retrievedEntity = (MyEntity) session.get(MyEntity.class, entity.getId());

        // 验证实体是否被正确保存和检索
        assertNotNull(retrievedEntity);
        assertEquals("Test Property", retrievedEntity.getProperty());
    }

    // 其他单元测试方法可以在这里添加

    // 实体类MyEntity
    public static class MyEntity {
        private Long id;
        private String property;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }
}
