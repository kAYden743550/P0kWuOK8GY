// 代码生成时间: 2025-09-29 18:29:54
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

// 模型解释工具类
public class ModelExplanationTool {
    private static SessionFactory sessionFactory;

    // 静态代码块，用于初始化SessionFactory
    static {
        try {
            // 加载hibernate配置文件
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // 获取SessionFactory实例
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 定义实体类ModelExplain，用于存储模型解释信息
    public static class ModelExplain {
        private Long id;
        private String modelName;
        private String explanation;

        // 构造函数、getter和setter省略

        // toString方法，用于输出模型解释信息
        @Override
        public String toString() {
            return "Model Name: " + modelName + ", Explanation: " + explanation;
        }
    }

    // 保存模型解释信息的方法
    public void saveModelExplanation(ModelExplain modelExplain) {
        Session session = null;
        Transaction tx = null;
        try {
            // 获取Session
            session = sessionFactory.openSession();
            // 开启事务
            tx = session.beginTransaction();
            // 保存模型解释信息
            session.save(modelExplain);
            // 提交事务
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // 获取所有模型解释信息的方法
    public List<ModelExplain> getAllModelExplanations() {
        List<ModelExplain> modelExplains = null;
        Session session = null;
        try {
            // 获取Session
            session = sessionFactory.openSession();
            // 获取所有模型解释信息
            modelExplains = session.createQuery("from ModelExplanationTool.ModelExplain", ModelExplain.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return modelExplains;
    }

    // 主方法，用于演示模型解释工具的使用
    public static void main(String[] args) {
        ModelExplanationTool tool = new ModelExplanationTool();
        ModelExplain modelExplain1 = new ModelExplain();
        modelExplain1.setModelName("Model1");
        modelExplain1.setExplanation("Explanation for Model1");

        ModelExplain modelExplain2 = new ModelExplain();
        modelExplain2.setModelName("Model2");
        modelExplain2.setExplanation("Explanation for Model2");

        // 保存模型解释信息
        tool.saveModelExplanation(modelExplain1);
        tool.saveModelExplanation(modelExplain2);

        // 获取并输出所有模型解释信息
        List<ModelExplain> modelExplains = tool.getAllModelExplanations();
        for (ModelExplain explain : modelExplains) {
            System.out.println(explain);
        }
    }
}