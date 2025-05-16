package com.home.mec888.dao;

import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Service;
import com.home.mec888.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppointmentDao {

    public void saveAppointment(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(appointment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateAppointment(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(appointment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Appointment getAppointmentById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Appointment.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Appointment> getAllAppointments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Appointment", Appointment.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Appointment> getAppointmentsLast15Days() {
        LocalDate today = LocalDate.now();
        LocalDate fromDate = today.minusDays(15);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Appointment where appointmentDate between :fromDate and :toDate order by appointmentDate asc", Appointment.class)
                    .setParameter("fromDate", java.sql.Date.valueOf(fromDate))
                    .setParameter("toDate", java.sql.Date.valueOf(today))
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        AppointmentDao dao = new AppointmentDao();
        List<Appointment> allAppointments = dao.getAppointmentsLast15Days();

        if (allAppointments != null && !allAppointments.isEmpty()) {
            for (Appointment appointment : allAppointments) {
                System.out.println("Patient name         : " + (appointment.getPatient() != null ? appointment.getPatient().getUser().getFullName() : "null"));
                System.out.println("Doctor name          : " + (appointment.getDoctor() != null ? appointment.getDoctor().getUser().getFullName() : "null"));
                System.out.println("Doctor Field          : " + (appointment.getDoctor() != null ? appointment.getDoctor().getSpecialization() : "null"));
                System.out.println("Appointment Date   : " + appointment.getAppointmentDate());
                System.out.println("Appointment Time   : " + appointment.getAppointmentTime());
                System.out.println("Status             : " + appointment.getStatus());
                System.out.println("--------------------------------------");
            }
        } else {
            System.out.println("No appointments found or an error occurred.");
        }
    }
    public void deleteAppointment(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Appointment appointment = session.get(Appointment.class, id);
            if (appointment != null) {
                session.delete(appointment);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // find appointment by appointment_date and doctor_id and appointment_time
    public List<Appointment> findAppointmentsByDateAndDoctorIdAndAppointmentTime(LocalDate appointmentDate, Long doctorId, LocalDate appointmentTime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Appointment where appointmentDate = :appointmentDate and doctor.id = :doctorId and appointmentTime = :appointmentTime", Appointment.class)
                    .setParameter("appointmentDate", appointmentDate)
                    .setParameter("doctorId", doctorId)
                    .setParameter("appointmentTime", appointmentTime)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Appointment> getAppointmentByDoctorIdWithStatusScheduledOrConfirmed(long doctorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Appointment where doctor.id = :doctorId and status in ('scheduled', 'confirmed')", Appointment.class)
                    .setParameter("doctorId", doctorId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Appointment> getAppointmentsServiceByDoctorId(long doctorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT DISTINCT a.* FROM appointments a " +
                    "LEFT JOIN doctors d1 ON d1.id = a.doctor_id " +
                    "LEFT JOIN treatment_steps ts ON a.id = ts.appointment_id " +
                    "LEFT JOIN treatment_steps_service tss ON ts.id = tss.treatment_step_id " +
                    "LEFT JOIN services s ON tss.service_id = s.id " +
                    "LEFT JOIN room r ON r.id = s.room_id " +
                    "LEFT JOIN doctors d2 ON d2.room_id = r.id " +
                    "WHERE d1.id = :doctorId OR d2.id = :doctorId " +
                    "AND ts.status = 'PENDING'";

            Query query = session.createNativeQuery(sql, Appointment.class);
            query.setParameter("doctorId", doctorId);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }


    public List<Appointment> getAppointmentWhereStatusIsCompleted() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Appointment where status = 'completed'", Appointment.class)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    public List<Service> getServiceByAppointmentId(Long appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT s.* FROM services s " +
                    "JOIN treatment_steps_service tss ON s.id = tss.service_id " +
                    "JOIN treatment_steps ts ON ts.id = tss.treatment_step_id " +
                    "JOIN appointments a ON a.id = ts.appointment_id " +
                    "WHERE a.id = :appointmentId";

            return session.createNativeQuery(sql, Service.class)
                    .setParameter("appointmentId", appointmentId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }
}