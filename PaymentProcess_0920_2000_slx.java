// 代码生成时间: 2025-09-20 20:00:54
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class PaymentProcess {
    
    // 获取SessionFactory
    private static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
    
    // 新建支付记录
    public void createPaymentRecord(Payment payment) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // 查询支付记录
    public List<Payment> getPaymentRecords() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("FROM Payment").list();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // 更新支付状态
    public void updatePaymentStatus(Integer paymentId, String status) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, paymentId);
            if (payment != null) {
                payment.setStatus(status);
                session.update(payment);
                transaction.commit();
            } else {
                transaction.rollback();
                throw new IllegalArgumentException("Payment not found with ID: " + paymentId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // 定义支付实体
    public static class Payment {
        private Integer id;
        private String status;
        private double amount;
        
        // 省略getter和setter方法
    }
}