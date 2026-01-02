package com.fita.vnua.credit;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserSubjectDAO {

    public static List<UserSubject> getAllUserSubjects() {
        Transaction transaction = null;
        List<UserSubject> list = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            list = session.createQuery("from UserSubject", UserSubject.class).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return list;
    }

    @SuppressWarnings("deprecation")
	public static boolean insertUserSubject(UserSubject us) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(us);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return false;
    }

    @SuppressWarnings("deprecation")
	public static boolean updateUserSubject(UserSubject us) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(us);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return false;
    }

    @SuppressWarnings("deprecation")
	public static boolean deleteUserSubject(int userSubjectId) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            UserSubject us = session.get(UserSubject.class, userSubjectId);
            if (us != null) {
                session.delete(us);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return false;
    }
}
