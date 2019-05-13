package ru.morou;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.morou.Info.User;

import javax.management.Query;
import java.util.Iterator;
import java.util.List;

public class DBWorkFlow {


    List<User> userList = null;

    public void connect() {

        SessionFactory factory = new Configuration ()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        Session session = null;
        try {
//            CREATE DONE
//            session = factory.getCurrentSession();
//            User newuser = new User ("Ola", "Ola", "OlaXLL", "Ola@mail.ru", "1234");
//            session.beginTransaction();
//            session.save(newuser);
//            session.getTransaction().commit();

//            AUTH
//            session = factory.getCurrentSession ();
//            session.beginTransaction();
//            Query sgl = session.createQuery ("select login from User");
//            List logins = ((org.hibernate.query.Query) sgl).list ();
//            for (Iterator iterator = logins.iterator (); iterator.hasNext();) {
//                String login = (String) iterator.next();
//                System.out.println("First Name" + login);
//            }
//            session.getTransaction().commit();


//            CHANGE_PASS DONE
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            User user = session.get(User.class, 2);
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

//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            userList = session.createQuery ("from User").list ();
//            System.out.println (userList);
//            if(userList != null && !userList.isEmpty ()) {
//                for (User user : userList) {
//                    System.out.println (user.getLogin ());
//                }
//            }
//            session.getTransaction().commit();

        } finally {
            factory.close();
            session.close();
        }
    }
}

