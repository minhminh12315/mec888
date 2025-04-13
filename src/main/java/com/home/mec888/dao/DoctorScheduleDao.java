package com.home.mec888.dao;

import com.home.mec888.entity.DoctorSchedule;
import com.home.mec888.entity.Room;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorScheduleDao {
    public void save(DoctorSchedule doctorSchedule) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(doctorSchedule);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public DoctorSchedule findByDayAndShift(String dayOfWeek, String startTime, String endTime, Room room, LocalDate workDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM DoctorSchedule ds " +
                                    "WHERE ds.dayOfWeek = :dayOfWeek " +
                                    "AND ds.startTime = :startTime " +
                                    "AND ds.endTime = :endTime " +
                                    "AND ds.doctor.room = :room " +
                                    "AND ds.workDate = :workDate",
                            DoctorSchedule.class)
                    .setParameter("dayOfWeek", dayOfWeek)
                    .setParameter("startTime", Time.valueOf(startTime))
                    .setParameter("endTime", Time.valueOf(endTime))
                    .setParameter("room", room)
                    .setParameter("workDate", workDate)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<LocalDate> findWorkDatesByDoctorInMonth(Long doctorId, int year, int month) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT ds.workDate " +
                                    "FROM DoctorSchedule ds " +
                                    "WHERE ds.doctor.id = :doctorId " +
                                    "AND YEAR(ds.workDate) = :year " +
                                    "AND MONTH(ds.workDate) = :month " +
                                    "ORDER BY ds.workDate", LocalDate.class)
                    .setParameter("doctorId", doctorId)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Find Start and End Time by Work Date
    public List<DoctorSchedule> findStartAndEndTimeByWorkDate(LocalDate workDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT ds " +
                                    "FROM DoctorSchedule ds " +
                                    "WHERE ds.workDate = :workDate", DoctorSchedule.class)
                    .setParameter("workDate", workDate)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
