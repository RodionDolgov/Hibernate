package org.example;

import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //create
            Person person = new Person("Bob", 85);
            session.save(person);
            //update
            Person person2 = session.get(Person.class, 2);
            person2.setName("Changed Name");
            //delete
            session.delete(person);

            session.getTransaction().commit();
            System.out.println(person.getId());
        } finally {
            sessionFactory.close();
        }
    }
}
