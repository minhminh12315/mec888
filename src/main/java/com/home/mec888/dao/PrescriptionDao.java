package com.home.mec888.dao;

import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.MedicalRecord;
import com.home.mec888.entity.Prescription;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PrescriptionDao {
    public Prescription savePrescription(Prescription prescription) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prescription);
            transaction.commit();
            return prescription;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return prescription;
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

    public List<Prescription> getAllPrescription() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Prescription ORDER BY createdAt DESC", Prescription.class).list();
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

    public Prescription getPrescriptionByRecord(MedicalRecord record) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Prescription where record = :record", Prescription.class)
                    .setParameter("record", record)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}