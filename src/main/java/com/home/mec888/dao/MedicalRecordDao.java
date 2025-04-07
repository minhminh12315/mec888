package com.home.mec888.dao;

import com.home.mec888.entity.MedicalRecord;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MedicalRecordDao {

    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(medicalRecord);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(medicalRecord);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public MedicalRecord getMedicalRecordById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MedicalRecord.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MedicalRecord order by updatedAt desc", MedicalRecord.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMedicalRecord(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MedicalRecord medicalRecord = session.get(MedicalRecord.class, id);
            if (medicalRecord != null) {
                session.delete(medicalRecord);
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