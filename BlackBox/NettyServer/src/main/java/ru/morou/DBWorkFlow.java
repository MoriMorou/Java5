package ru.morou;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.morou.Info.User;

import java.util.List;

public class DBWorkFlow {

    public void connect() {

        SessionFactory factory = new Configuration ()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        Session session = null;
        try {
//            CREATE
//            session = factory.getCurrentSession();
//            User newuser = new User ("Oma", "Gal", "Oma", "Oma@mail.ru", "1234");
//            session.beginTransaction();
//            session.save(newuser);
//            session.getTransaction().commit();

//            AUTH
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            User user = session.get(User.class, 1);
//            session.getTransaction().commit();
//            System.out.println(user);

//            CHANGE_PASS
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            User user = session.get(User.class, 1);
//            user.setPassword ("4321");
//            session.getTransaction().commit();
//            System.out.println(user);

//            DeleteUser (не работает)
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            List<User> userNick = session.createQuery ("from User u where u.login = 'Oma'").getResultList ();
//            session.delete(userNick);
//            session.getTransaction().commit();

//            AUTH
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            List<User> userNick = session.createQuery ("from User u where u.login = 'Oma'").getResultList ();
//            System.out.println(userNick);
//            session.getTransaction().commit();

//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            session.createQuery("update Book set title = 'A'").executeUpdate();
//            session.createQuery("delete from Book where id = 3").executeUpdate();
//            session.getTransaction().commit();
//
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            Book book = session.get(Book.class, 1);
//            System.out.println(book);
//            session.getTransaction().commit();
//
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            Author author = session.get(Author.class, 1);
//            System.out.println(author);
//            session.getTransaction().commit();

//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            List<Reader> readers = session.createQuery("from Reader").getResultList();
//            System.out.println(readers);
//            session.getTransaction().commit();

//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            Author author = session.get(Author.class, 2);
//            session.delete(author);
//            session.getTransaction().commit();
        } finally {
            factory.close();
            session.close();
        }
    }
}

