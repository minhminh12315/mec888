package com.home.mec888.dao;

import com.home.mec888.entity.Medicine;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MedicineDao {

    public void saveMedicine(Medicine medicine) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(medicine);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateMedicine(Medicine medicine) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(medicine);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Medicine getMedicineById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Medicine.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Medicine> getAllMedicines() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Medicine order by createdAt desc", Medicine.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMedicine(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Medicine medicine = session.get(Medicine.class, id);
            if (medicine != null) {
                session.delete(medicine);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Medicine getMedicineByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Medicine where lower(name) = :name", Medicine.class)
                    .setParameter("name", name.toLowerCase())
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Medicine> getMedicinesBySelectedAppointmentId(Long appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Use native SQL query instead of HQL to avoid entity resolution issues
            String sql = "SELECT m.* FROM medicines m " +
                    "JOIN prescription_details pd ON pd.medicine_id = m.id " +
                    "JOIN prescriptions p ON pd.prescription_id = p.id " +
                    "JOIN medical_records mr ON p.record_id = mr.id " +
                    "WHERE mr.appointment_id = :appointmentId";

            Query<Medicine> query = session.createNativeQuery(sql, Medicine.class)
                    .setParameter("appointmentId", appointmentId);

            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error loading medicines: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}