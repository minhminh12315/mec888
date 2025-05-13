package com.home.mec888.dao;

import com.home.mec888.entity.TreatmentSteps;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class TreatmentStepDao {

    public TreatmentSteps saveTreatmentStep(TreatmentSteps treatmentStep) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(treatmentStep);
            transaction.commit();
            return treatmentStep;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public void updateTreatmentStep(TreatmentSteps treatmentStep) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(treatmentStep);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public TreatmentSteps getTreatmentStepById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(TreatmentSteps.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TreatmentSteps> getAllTreatmentSteps() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TreatmentSteps ORDER BY createdAt DESC", TreatmentSteps.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTreatmentStep(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TreatmentSteps treatmentStep = session.get(TreatmentSteps.class, id);
            if (treatmentStep != null) {
                session.delete(treatmentStep);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public List<TreatmentSteps> getAllTreatmentStepsByAppointmentId(Long appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TreatmentSteps WHERE appointment.id = :appointmentId", TreatmentSteps.class)
                    .setParameter("appointmentId", appointmentId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TreatmentSteps> getAllTreatmentStepsByAppointmentIdAndRoomId(Long appointmentId, Long roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT DISTINCT ts.* FROM treatment_steps ts " +
                    "JOIN treatment_steps_service tss ON tss.treatment_step_id = ts.id " +
                    "JOIN services s ON s.id = tss.service_id " +
                    "JOIN room r ON r.id = s.room_id " +
                    "WHERE ts.appointment_id = :appointmentId AND r.id = :roomId";

            Query<TreatmentSteps> query = session.createNativeQuery(sql, TreatmentSteps.class);
            query.setParameter("appointmentId", appointmentId);
            query.setParameter("roomId", roomId);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Tránh lỗi NullPointerException
        }
    }
}