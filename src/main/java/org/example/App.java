package org.example;

import org.example.models.Item;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //Get items
            Person person = session.get(Person.class, 1);
            System.out.println(person);
            List<Item> items = person.getItems();
            for (Item item : items) {
                System.out.println(item.getItemName());
            }

            //Get owner
            Item item = session.get(Item.class, 3);
            System.out.println(item.getOwner());
            //Add new Item
            Person secondPerson = session.get(Person.class, 2);
            Item newItem = new Item("Chair", secondPerson);
            secondPerson.getItems().add(newItem);
            session.save(newItem);
            //Add new Person
            Person newPerson = new Person("Test Person", 41);
            newPerson.setItems(new ArrayList<>(Collections.singletonList(newItem)));
            session.save(newPerson);
            //Delete person's items
            List<Item> delItems = person.getItems();
            for (Item singleItem : delItems) {
                session.remove(singleItem);
            }

            person.getItems().clear();

            //Delete person (check cascade operations)
            Person person2 = session.get(Person.class, 2);
            session.remove(person2);
            //Hibernate cash (refresh)
            person2.getItems().forEach(t -> t.setOwner(null));

            //Change owner
            Item item3 = session.get(Item.class, 3);
            Person person6 = session.get(Person.class, 6);
            Optional.ofNullable(item3)
                    .map(Item::getOwner)
                    .map(Person::getItems)
                    .ifPresent(i -> i.remove(item3));
            ;
            item3.setOwner(person6);
            person6.getItems().add(item3);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
