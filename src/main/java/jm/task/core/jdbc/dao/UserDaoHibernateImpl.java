package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS UserTable (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT)";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();;
        }
    }
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS UserTable";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();;
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "SELECT * FROM UserTable";
            return session.createCriteria(User.class).list();
        }
    }

    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String sql = "DELETE FROM UserTable";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        }
    }
}
