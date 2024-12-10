package com.utad.dao.imp;

import com.utad.dao.interfaces.UserDAO;
import com.utad.entity.User;
import com.utad.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDAOImpl implements UserDAO {

    @Override
    public void save(User user) {
        executeTransaction(session -> session.save(user));
    }

    @Override
    public User findById(Long id) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void update(User user) {
        executeTransaction(session -> session.update(user));
    }

    @Override
    public void delete(User user) {
        executeTransaction(session -> {
            User userToDelete = session.get(User.class, user.getId());
            if (userToDelete != null) {
                userToDelete.getTickets().clear();
                session.delete(userToDelete);
            }
        });
    }

    private void executeTransaction(HibernateTransactionAction action) {
        Transaction transaction = null;
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            action.execute(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface HibernateTransactionAction {
        void execute(Session session);
    }
}
