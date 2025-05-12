package com.home.mec888.dao;

import com.home.mec888.entity.PrescriptionDetail;
import com.home.mec888.entity.Medicine;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionDetailDao {

    public void savePrescriptionDetail(PrescriptionDetail prescriptionDetail) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prescriptionDetail);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updatePrescriptionDetail(PrescriptionDetail prescriptionDetail) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(prescriptionDetail);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public PrescriptionDetail getPrescriptionDetailById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PrescriptionDetail.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrescriptionDetail> getAllPrescriptionDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from PrescriptionDetail", PrescriptionDetail.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePrescriptionDetail(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            PrescriptionDetail prescriptionDetail = session.get(PrescriptionDetail.class, id);
            if (prescriptionDetail != null) {
                session.delete(prescriptionDetail);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<PrescriptionDetail> getPrescriptionDetailsByPrescriptionId(Long prescriptionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from PrescriptionDetail where prescription.id = :prescriptionId", PrescriptionDetail.class)
                    .setParameter("prescriptionId", prescriptionId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrescriptionDetail> getPrescriptionDetailsByMedicineId(Long medicineId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from PrescriptionDetail where medicine.id = :medicineId", PrescriptionDetail.class)
                    .setParameter("medicineId", medicineId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<PrescriptionDetail> getPrescriptionsBySelectedAppointmentId(Long appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Use native SQL query instead of HQL
            String sql = "SELECT pd.* FROM prescription_details pd " +
                    "JOIN prescriptions p ON pd.prescription_id = p.id " +
                    "JOIN medical_records mr ON p.record_id = mr.id " +
                    "WHERE mr.appointment_id = :appointmentId";

            Query<PrescriptionDetail> query = session.createNativeQuery(sql, PrescriptionDetail.class)
                    .setParameter("appointmentId", appointmentId);

            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}