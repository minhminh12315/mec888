package com.home.mec888.dao;

import com.home.mec888.entity.Payment;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDao {

    public boolean savePayment(Payment payment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(payment); // Save the payment object to the database
            transaction.commit(); // Commit the transaction
            return true; // Successfully saved
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
            return false; // Failed to save
        }
    }

    public void updatePayment(Payment payment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(payment); // Update the payment object
            transaction.commit(); // Commit the transaction
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
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