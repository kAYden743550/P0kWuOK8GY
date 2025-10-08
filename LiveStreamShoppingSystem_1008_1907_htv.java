// 代码生成时间: 2025-10-08 19:07:00
// LiveStreamShoppingSystem.java
// This Java program uses Hibernate to create a live stream shopping system.

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.selector.SimpleStrategyRegistrationImpl;
import org.hibernate.boot.registry.selector.StrategySelector;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.query.Query;
import java.util.List;

// Entity class representing a Product
class Product {
    private Long id;
    private String name;
    private double price;

    public Product() {}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

// Entity class representing a LiveStream
class LiveStream {
    private Long id;
    private String title;
    private Product product;

    public LiveStream() {}

    public LiveStream(String title, Product product) {
        this.title = title;
        this.product = product;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

// DAO class for Product operations
class ProductDAO {
    private SessionFactory factory;

    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void addProduct(Product product) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Product> listAllProducts() {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            return query.getResultList();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Product getProductById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            return session.get(Product.class, id);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

// DAO class for LiveStream operations
class LiveStreamDAO {
    private SessionFactory factory;

    public LiveStreamDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void addLiveStream(LiveStream liveStream) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(liveStream);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<LiveStream> listAllLiveStreams() {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<LiveStream> query = session.createQuery("FROM LiveStream", LiveStream.class);
            return query.getResultList();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public LiveStream getLiveStreamById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            return session.get(LiveStream.class, id);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

// Main class to run the application
public class LiveStreamShoppingSystem {
    public static void main(String[] args) {
        // Initialize Product and LiveStream DAOs
        ProductDAO productDao = new ProductDAO();
        LiveStreamDAO liveStreamDao = new LiveStreamDAO();

        // Create some products
        Product product1 = new Product("Smartphone", 999.99);
        Product product2 = new Product("Laptop", 1299.99);

        // Add products to the database
        productDao.addProduct(product1);
        productDao.addProduct(product2);

        // Create a live stream
        LiveStream liveStream = new LiveStream("Tech Gadgets Sale", product1);

        // Add live stream to the database
        liveStreamDao.addLiveStream(liveStream);

        // List all products
        List<Product> products = productDao.listAllProducts();
        for (Product product : products) {
            System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice());
        }

        // List all live streams
        List<LiveStream> liveStreams = liveStreamDao.listAllLiveStreams();
        for (LiveStream liveStream1 : liveStreams) {
            System.out.println("Live Stream ID: " + liveStream1.getId() + ", Title: " + liveStream1.getTitle() + ", Product Name: " + liveStream1.getProduct().getName());
        }
    }
}"}