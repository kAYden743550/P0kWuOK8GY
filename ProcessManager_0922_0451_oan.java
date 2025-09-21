// 代码生成时间: 2025-09-22 04:51:23
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "processes")
public class Process {
    @Id
    private Long id;
    private String name;
    private String status;
    
    public Process() {}
    
    public Process(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProcessManager {
    private SessionFactory sessionFactory;
    
    public ProcessManager() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    
    public void addProcess(Process process) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(process);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removeProcess(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Process process = session.get(Process.class, id);
            if (process != null) {
                session.delete(process);
                transaction.commit();
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Process> getAllProcesses() {
        try (Session session = sessionFactory.openSession()) {
            List<Process> processes = session.createQuery("FROM Process", Process.class).list();
            return processes;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public void close() {
        sessionFactory.close();
    }
}
