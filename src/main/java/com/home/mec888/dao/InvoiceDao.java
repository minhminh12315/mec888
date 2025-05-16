package com.home.mec888.dao;

import com.home.mec888.entity.Invoice;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class InvoiceDao {

    public void saveInvoice(Invoice invoice) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(invoice);
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

    public void updateInvoice(Invoice invoice) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(invoice);
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