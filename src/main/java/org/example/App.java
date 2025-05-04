package org.example;

import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //update, read, delete
            session.createQuery("update Person set name = 'newName' where age = 23").executeUpdate();
            List<Person> personByAge = session.createQuery("from Person where age > 55").getResultList();
            for(Person person: personByAge) {
                System.out.println(person);
            }
            List<Person> personByName = session.createQuery("from Person where name like 'W%'").getResultList();
            for(Person person: personByName) {
                System.out.println(person);
            }
            session.createQuery("delete Person where age > 84").executeUpdate();

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
