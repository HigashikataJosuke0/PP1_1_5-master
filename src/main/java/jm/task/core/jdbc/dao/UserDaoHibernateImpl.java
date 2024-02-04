package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "create table if NOT EXISTS users\n" +
                "(\n" +
                "    id       bigint auto_increment\n" +
                "        primary key,\n" +
                "    age      tinyint      null,\n" +
                "    lastName varchar(255) null,\n" +
                "    name     varchar(255) null\n" +
                ")\n" +
                "    engine = MyISAM;\n";
        try {
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP Table IF EXISTS users";
        try {

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            session.close();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "INSERT INTO users (name, lastName, age) "
                + "VALUES(?, ?, ?)";
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            //session.persist(query);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM users WHERE ID = ?";
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }


    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> list = new ArrayList<>();
        Transaction transaction = session.beginTransaction();
        String sql = "from User";
        try {
            session.get(User.class, 1L);
            Query query = session.createQuery(sql);
            list = query.getResultList();
            session.close();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return list;
    }


    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM users";
        try {
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            session.close();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
