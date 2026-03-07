package com.fita.vnua.credit;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class SubjectDAO {

	public static List<Subject> getAllSubjects() {
	    Transaction transaction = null;
	    List<Subject> listOfSubjects = null;
	    Session session = null;

	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        transaction = session.beginTransaction();

	        listOfSubjects = session.createQuery("from Subject", Subject.class).getResultList();

	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
	            try {
	                transaction.rollback();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return listOfSubjects;
	}
    @SuppressWarnings("deprecation")
	public static boolean insertSubject(Subject subject) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(subject);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("deprecation")
	public static boolean updateSubject(Subject subject) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(subject);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static Subject getSubjectByCode(String subjectCode) {
        Transaction transaction = null;
        Subject subject = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            subject = session.get(Subject.class, subjectCode);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return subject;
    }
}
