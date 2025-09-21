// 代码生成时间: 2025-09-21 18:40:51
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

// 定义实体类，代表数据模型
class User {
    private Long id;
    private String name;
    private String email;

    // 省略构造方法、getter和setter方法
}

// 定义Hibernate Utility类，用于获取SessionFactory实例
class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

// 定义DAO类，用于处理数据库操作
class UserDao {
    private SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<User> listUsers() {
        List<User> users = null;
        Session session = sessionFactory.openSession();
        try {
            users = session.createQuery("FROM User", User.class).getResultList();
        } finally {
            session.close();
        }
        return users;
    }

    // 省略其他CRUD方法
}

// 定义Service类，用于业务逻辑处理
class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        userDao.addUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.listUsers();
    }

    // 省略其他业务方法
}

// 主类，用于程序入口
public class HibernateDataModelExample {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserDao userDao = new UserDao(sessionFactory);
        UserService userService = new UserService(userDao);

        // 创建并添加用户
        User newUser = new User();
        // 设置用户属性
        userService.createUser(newUser);

        // 获取并打印所有用户
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user.getName() + " " + user.getEmail());
        }
    }
}