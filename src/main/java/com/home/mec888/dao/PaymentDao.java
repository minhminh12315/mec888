package com.home.mec888.dao;

import com.home.mec888.entity.Payment;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDao {

    public void savePayment(Payment payment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;  // Re-throw to handle in controller
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updatePayment(Payment payment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(payment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Payment getPaymentById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Payment.class, id); // Fetch the payment by its ID
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    public List<Payment> getAllPayments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Payment ORDER BY createdAt DESC", Payment.class).list(); // Fetch all payments
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    public void deletePayment(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Payment payment = session.get(Payment.class, id); // Fetch the payment by its ID
            if (payment != null) {
                session.delete(payment); // Delete the payment
                transaction.commit(); // Commit the transaction
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
        }
    }

    public List<Payment> getPaymentsByPatientId(Long patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Payment WHERE patient.id = :patientId ORDER BY createdAt DESC";
            return session.createQuery(hql, Payment.class)
                    .setParameter("patientId", patientId)
                    .list(); // Fetch payments by patient ID
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }
}