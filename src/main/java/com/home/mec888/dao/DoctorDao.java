package com.home.mec888.dao;

import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.DoctorSchedule;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {
    public void saveDoctor(Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(doctor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public List<Appointment> findAppointmentsByDoctorId(Long doctorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Appointment a WHERE a.doctor.id = :doctorId ORDER BY a.appointmentDate DESC, a.appointmentTime DESC",
                            Appointment.class)
                    .setParameter("doctorId", doctorId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Doctor> getAllDoctors() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Doctor order by updatedAt desc", Doctor.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteDoctor(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, id);
            if (doctor != null) {
                session.delete(doctor);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateDoctor(Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(doctor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Doctor getDoctorById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Doctor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // findDoctorByWorkDate
    public List<Doctor> findDoctorByWorkDate(LocalDate workDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                                    "FROM DoctorSchedule ds " +
                                    "join ds.doctor d " +
                                    "WHERE ds.workDate = :workDate", Doctor.class)
                    .setParameter("workDate", workDate)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Doctor findDoctorByUserId(long userID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Doctor d WHERE d.user.id = :userID", Doctor.class)
                    .setParameter("userID", userID)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Doctor findDoctorByRoomId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Doctor d WHERE d.room.id = :id", Doctor.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
