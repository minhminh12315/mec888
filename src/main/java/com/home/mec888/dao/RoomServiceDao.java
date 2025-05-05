package com.home.mec888.dao;

import com.home.mec888.entity.RoomService;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoomServiceDao {

    public void saveRoomService(RoomService roomService) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(roomService);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateRoomService(RoomService roomService) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(roomService);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public RoomService getRoomServiceById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(RoomService.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RoomService> getAllRoomServices() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from RoomService", RoomService.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteRoomService(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            RoomService roomService = session.get(RoomService.class, id);
            if (roomService != null) {
                session.delete(roomService);
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