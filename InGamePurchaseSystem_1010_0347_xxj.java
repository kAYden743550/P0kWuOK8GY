// 代码生成时间: 2025-10-10 03:47:26
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.UUID;

// 实体类：商品
class Product {
    private String id;
    private String name;
    private double price;

    // 省略构造函数、getter和setter方法
}

// 实体类：订单
class PurchaseOrder {
    private String id;
    private String productId;
    private String userId;

    // 省略构造函数、getter和setter方法
}

// 游戏内购系统服务类
public class InGamePurchaseSystem {

    private SessionFactory sessionFactory;

    public InGamePurchaseSystem() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // 添加商品
    public void addProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 购买商品
    public boolean purchaseProduct(String productId, String userId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new PurchaseOrder(UUID.randomUUID().toString(), productId, userId));
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取用户订单
    public List<PurchaseOrder> getUserOrders(String userId) {
        try (Session session = sessionFactory.openSession()) {
            List<PurchaseOrder> orders = session.createQuery("from PurchaseOrder where userId = :userId", PurchaseOrder.class)
                    .setParameter("userId", userId)
                    .list();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 关闭SessionFactory
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // 主函数，用于测试
    public static void main(String[] args) {
        InGamePurchaseSystem system = new InGamePurchaseSystem();
        Product product = new Product();
        product.setName("Magic Sword");
        product.setPrice(99.99);
        system.addProduct(product);

        String userId = "user123";
        boolean purchaseSuccess = system.purchaseProduct("magicSwordId", userId);
        if (purchaseSuccess) {
            System.out.println("Purchase successful!");
        }

        List<PurchaseOrder> orders = system.getUserOrders(userId);
        if (orders != null) {
            for (PurchaseOrder order : orders) {
                System.out.println("Order ID: " + order.getId() + ", Product ID: " + order.getProductId());
            }
        }

        system.close();
    }
}