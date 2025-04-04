package com.home.mec888.dao;

import com.home.mec888.entity.Department;
import com.home.mec888.entity.Medicine;
import com.home.mec888.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DepartmentDao {
    public void saveDeparment(Department department){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Department> getAllDepartments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Department order by createdAt desc", Department.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
