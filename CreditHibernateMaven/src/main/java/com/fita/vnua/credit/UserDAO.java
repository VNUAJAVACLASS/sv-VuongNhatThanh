package com.fita.vnua.credit;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {

    public static List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getUserByCode(String userCode) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, userCode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addUser(User user) {
        if (getUserByCode(user.getUserCode()) != null) {
            System.err.println("❌ User code bị trùng: " + user.getUserCode());
            return false;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(String userCode) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userCode);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                return true;
            } else {
                System.err.println("❌ Không tìm thấy user để xóa: " + userCode);
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
