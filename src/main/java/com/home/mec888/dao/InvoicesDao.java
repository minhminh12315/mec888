package com.home.mec888.dao;

import com.home.mec888.entity.Invoices;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class InvoicesDao {

    public boolean saveInvoices(Invoices Invoices) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(Invoices);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public void updateInvoices(Invoices Invoices) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(Invoices);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Invoices getInvoicesById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Invoices.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public  List<Invoices> getAllInvoices() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Invoices ORDER BY createdAt DESC", Invoices.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Invoices> searchByInvoicesNumber(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Invoices WHERE InvoicesNumber LIKE :keyword";
            return session.createQuery(hql, Invoices.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteInvoices(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Invoices Invoices = session.get(Invoices.class, id);
            if (Invoices != null) {
                session.delete(Invoices);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
