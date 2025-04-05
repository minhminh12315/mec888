package com.home.mec888.dao;

import com.home.mec888.entity.User;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserDao {

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public User getUserById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User order by updatedAt desc", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUser(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User login(String username, String password) {
        try {
            User user = getUserByUsername(username);
            if (user != null) {
                String hashedPassword = user.getPassword();
                if(BCrypt.checkpw(password, hashedPassword)){
                    return user;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean isUsernameExists(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isEmailExists(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}