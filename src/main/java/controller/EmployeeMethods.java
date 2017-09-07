package controller;

import classes.Employee;
import models.EmployeeEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Iterator;
import java.util.List;


public class EmployeeMethods {

    public EmployeeEntity Insert(SessionFactory sessionFactory, String name, String surname, int salary) {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object " + ex);
            throw new ExceptionInInitializerError(ex);
        }
        EmployeeEntity et = new EmployeeEntity();
//		et.setEmployeeId(6);
        et.setName(name);
        et.setSurname(surname);
        et.setSalary(salary);
        insert(et, sessionFactory);
        return et;
    }


    public List<EmployeeEntity> List(SessionFactory sessionFactory) {

        List<EmployeeEntity> retVal = null;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object " + ex);
            throw new ExceptionInInitializerError(ex);
        }

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            List<EmployeeEntity> employees = (List<EmployeeEntity>) session.createQuery("FROM EmployeeEntity").list();
            retVal = employees;
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return retVal;
    }


    public EmployeeEntity getById(String id, SessionFactory sessionFactory) {
        EmployeeEntity retval = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            retval = session.get(EmployeeEntity.class, Integer.parseInt(id));
//            retval = (EmployeeEntity) session.createQuery("From EmployeeEntity AS e where e.employeeId =:id").setParameter("id", Integer.parseInt(id)).list().get(0);
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return retval;
    }

    public void DeleteEmployee(SessionFactory sessionFactory, Integer employeeId) {
        deleteEmployee(employeeId, sessionFactory);
    }

    private static void printAllEmployee(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Employee> employees = session.createQuery("FROM EmployeeEntity").list();
            System.out.println("-------------Employee List----------------");
            for (Iterator iterator1 =
                 employees.iterator(); iterator1.hasNext(); ) {
                Employee employee = (Employee) iterator1.next();
                System.out.print("First Name: " + employee.getName());
                System.out.print("  Last Name: " + employee.getSurname());
                System.out.println("  Salary: " + employee.getSalary());

            }
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    private static EmployeeEntity findBySurname(String surname, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        EmployeeEntity employee = null;
        try {
            tx = session.beginTransaction();
            employee = (EmployeeEntity) session.createQuery("From EmployeeEntity AS e where e.surname =:surname").setParameter("surname", surname).list().get(0); //ayni soyadiyla tek bir eleman var diye dusunulmustur.
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return employee;
    }

    private static void deleteEmployee(int employee_id, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            EmployeeEntity employee = session.get(EmployeeEntity.class, employee_id);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void updateEmployee(EmployeeEntity employee, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            EmployeeEntity emp = session.get(EmployeeEntity.class, employee.getEmployeeId());
            emp.setName(employee.getName());
            emp.setSurname(employee.getSurname());
            emp.setSalary(employee.getSalary());
            session.update(emp);
            tx.commit();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    private static void insert(EmployeeEntity employee, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(employee);
            System.out.println("employee obj: " + employee.toString());
            tx.commit();
            session.flush();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

}