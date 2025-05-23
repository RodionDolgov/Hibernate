package org.example;

import org.example.models.Item;
import org.example.models.Passport;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class)
                .addAnnotatedClass(Passport.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Person person = new Person("Test Olga", 12);
            Passport passport = new Passport(12323);
            person.setPassport(passport);

            session.save(person);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
