package com.home.mec888.dao;

import com.home.mec888.entity.Invoice;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class InvoiceDao {

    public boolean saveInvoice(Invoice invoice) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(invoice); // Save the invoice object to the database
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

    public void updateInvoice(Invoice invoice) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(invoice); // Update the invoice object
            transaction.commit(); // Commit the transaction
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
        }
    }

    public Invoice getInvoiceById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Invoice.class, id); // Fetch the invoice by its ID
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    public List<Invoice> getAllInvoices() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Invoice ORDER BY createdAt DESC", Invoice.class).list(); // Fetch all invoices
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    public void deleteInvoice(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Invoice invoice = session.get(Invoice.class, id); // Fetch the invoice by its ID
            if (invoice != null) {
                session.delete(invoice); // Delete the invoice
                transaction.commit(); // Commit the transaction
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
        }
    }

    public List<Invoice> getInvoicesByPaymentId(Long paymentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Invoice WHERE payment.id = :paymentId ORDER BY createdAt DESC";
            return session.createQuery(hql, Invoice.class)
                    .setParameter("paymentId", paymentId)
                    .list(); // Fetch invoices by payment ID
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }
}