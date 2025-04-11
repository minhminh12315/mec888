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
            // HQL query to search for rooms by roomNumber using LIKE
            String hql = "FROM Room WHERE roomNumber LIKE :roomNumber";

            // Using the LIKE operator with "%" for pattern matching
            List<Room> rooms = session.createQuery(hql, Room.class)
                    .setParameter("roomNumber", "%" + roomNumber + "%") // Add wildcards for partial matching
                    .getResultList(); // Use getResultList() to return all matching rooms

            return rooms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Debugging: Log if session is properly opened
            System.out.println("Session opened successfully.");

            List<Room> rooms = session.createQuery("from Room order by createdAt desc", Room.class).list();

            if (rooms == null || rooms.isEmpty()) {
                System.out.println("No rooms found.");
            } else {
                System.out.println("Rooms fetched: " + rooms.size());
            }

            return rooms;
        } catch (Exception e) {
            // Handle and log exception for debugging
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
