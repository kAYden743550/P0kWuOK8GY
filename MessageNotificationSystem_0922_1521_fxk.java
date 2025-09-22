// 代码生成时间: 2025-09-22 15:21:26
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.UUID;

public class MessageNotificationSystem {

    // Hibernate Session Factory
    private static SessionFactory sessionFactory;

    // Initialization block to setup the Session Factory
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Inner class representing a Notification Message
    public static class Message {
        private UUID id;
        private String content;
        private String recipient;

        // Constructor
        public Message(String content, String recipient) {
            this.id = UUID.randomUUID();
            this.content = content;
            this.recipient = recipient;
        }

        // Getters and Setters
        public UUID getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }
    }

    // Method to add a new message to the system
    public static Message addMessage(String content, String recipient) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Message message = new Message(content, recipient);
            session.save(message);
            tx.commit();
            return message;

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
        return null;
    }

    // Method to retrieve all messages for a given recipient
    public static List<Message> getMessagesForRecipient(String recipient) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Message where recipient = :recipient", Message.class)
                    .setParameter("recipient", recipient)
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    // Hibernate utility method to close the Session Factory
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void main(String[] args) {
        // Example usage of the MessageNotificationSystem
        try {
            Message message = addMessage("Hello, this is a test message!", "user@example.com");
            if (message != null) {
                System.out.println("Message added: " + message.getContent());
            }

            List<Message> messages = getMessagesForRecipient("user@example.com");
            if (messages != null) {
                for (Message msg : messages) {
                    System.out.println("Message Content: " + msg.getContent() + ", Recipient: " + msg.getRecipient());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSessionFactory();
        }
    }
}