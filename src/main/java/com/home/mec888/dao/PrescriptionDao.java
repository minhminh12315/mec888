package com.home.mec888.dao;

import com.home.mec888.entity.Prescription;
import com.home.mec888.entity.MedicalRecord;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public class PrescriptionDao {

    public void savePrescription(Prescription prescription) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prescription);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updatePrescription(Prescription prescription) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(prescription);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Prescription getPrescriptionById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Prescription.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Prescription> getAllPrescriptions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Prescription", Prescription.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePrescription(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Prescription prescription = session.get(Prescription.class, id);
            if (prescription != null) {
                session.delete(prescription);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Prescription> getPrescriptionsByMedicalRecordId(Long recordId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Prescription where medicalRecord.id = :recordId", Prescription.class)
                    .setParameter("recordId", recordId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Prescription> getPrescriptionsByDate(Date issuedDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Prescription where issuedDate = :issuedDate", Prescription.class)
                    .setParameter("issuedDate", issuedDate)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}