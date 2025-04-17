package com.home.mec888.dao;

import com.home.mec888.entity.Department;
import com.home.mec888.entity.Room;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoomDao {

    public boolean saveRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(room);  // Save the room object to the database
            transaction.commit(); // Commit the transaction
            return true; // Successfully saved
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // Rollback the transaction if an error occurs
            }
            e.printStackTrace();
            return false; // Failed to save
        }
    }


    public void updateRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Room> getRoomByRoomNumber(String roomNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL join Room với Department, dùng LIKE cho roomNumber
            String hql = "SELECT r FROM Room r JOIN FETCH r.department " +
                    "WHERE r.roomNumber LIKE :roomNumber";

            List<Room> rooms = session.createQuery(hql, Room.class)
                    .setParameter("roomNumber", "%" + roomNumber + "%")
                    .getResultList();

            return rooms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Session opened successfully.");

            // JOIN FETCH để lấy luôn thông tin phòng ban đi kèm
            List<Room> rooms = session.createQuery(
                    "SELECT r FROM Room r JOIN FETCH r.department ORDER BY r.createdAt DESC", Room.class
            ).list();

            if (rooms == null || rooms.isEmpty()) {
                System.out.println("No rooms found.");
            } else {
                System.out.println("Rooms fetched: " + rooms.size());
                // In thử tên phòng ban
                for (Room room : rooms) {
                    System.out.println("Room: " + room.getRoomNumber() + " | Department: " + room.getDepartment().getName());
                }
            }

            return rooms;
        } catch (Exception e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }



    public void deleteRoom(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, id);
            if (room != null) {
                session.delete(room);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Room getRoomByNumber(String roomNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room where lower(roomNumber) = :roomNumber", Room.class)
                    .setParameter("roomNumber", roomNumber)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Room getRoomById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
