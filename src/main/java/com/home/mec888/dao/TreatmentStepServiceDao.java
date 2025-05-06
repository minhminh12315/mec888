package com.home.mec888.dao;

import com.home.mec888.entity.TreatmentStepServices;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TreatmentStepServiceDao {

    public TreatmentStepServices saveTreatmentStepService(TreatmentStepServices treatmentStepService) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(treatmentStepService);
            transaction.commit();
            return treatmentStepService;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public void updateTreatmentStepService(TreatmentStepServices treatmentStepService) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(treatmentStepService);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public TreatmentStepServices getTreatmentStepServiceById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(TreatmentStepServices.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TreatmentStepServices> getAllTreatmentStepServices() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TreatmentStepServices ORDER BY createdAt DESC", TreatmentStepServices.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTreatmentStepService(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TreatmentStepServices treatmentStepService = session.get(TreatmentStepServices.class, id);
            if (treatmentStepService != null) {
                session.delete(treatmentStepService);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public TreatmentStepServices getTreatmentStepServiceByTreatmentStepID(Long treatmentStepId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TreatmentStepServices WHERE treatmentStep.id = :treatmentStepId", TreatmentStepServices.class)
                    .setParameter("treatmentStepId", treatmentStepId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}