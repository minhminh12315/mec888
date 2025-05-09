package com.home.mec888.dao;

import com.home.mec888.entity.Prescription;
import com.home.mec888.entity.PrescriptionDetails;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PrescriptionDetailsDao {
    public PrescriptionDetails savePrescriptionDetails(PrescriptionDetails prescriptionDetails) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prescriptionDetails);
            transaction.commit();
            return prescriptionDetails;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public void saveAllPrescriptionDetails(List<PrescriptionDetails> details) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (PrescriptionDetails d : details) {
                session.save(d);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updatePrescriptionDetails(PrescriptionDetails prescriptionDetails) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(prescriptionDetails);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public PrescriptionDetails getPrescriptionDetailsById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PrescriptionDetails.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrescriptionDetails> getAllPrescriptionDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM PrescriptionDetails ORDER BY createdAt DESC", PrescriptionDetails.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePrescriptionDetails(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            PrescriptionDetails prescriptionDetails = session.get(PrescriptionDetails.class, id);
            if (prescriptionDetails != null) {
                session.delete(prescriptionDetails);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public PrescriptionDetails getPrescriptionDetailsByPrescriptionID(Prescription prescription) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM PrescriptionDetails WHERE prescription = :prescription", PrescriptionDetails.class)
                    .setParameter("prescription", prescription)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
