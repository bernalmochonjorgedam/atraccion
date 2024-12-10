package com.utad.dao.imp;

import com.utad.dao.interfaces.TicketDAO;
import com.utad.entity.Ticket;
import com.utad.util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    @Override
    public void save(Ticket ticket) {
        executeTransaction(session -> session.save(ticket));
    }

    @Override
    public Ticket findById(Long id) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    @Override
    public List<Ticket> findAll() {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("from Ticket", Ticket.class).list();
        }
    }

    @Override
    public void update(Ticket ticket) {
        executeTransaction(session -> session.update(ticket));
    }

    @Override
    public void delete(Ticket ticket) {
        executeTransaction(session -> session.delete(ticket));
    }

    @Override
    public List<Ticket> findByUserId(Long userId) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("from Ticket where user.id = :userId", Ticket.class)
                    .setParameter("userId", userId).list();
        }
    }

    @Override
    public Double getAverageSpendingByUser(Long userId) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return (Double) session.createQuery("select avg(price) from Ticket where user.id = :userId")
                    .setParameter("userId", userId).uniqueResult();
        }
    }

    @Override
    public List<Ticket> findByPriceRange(double min, double max) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("from Ticket where price between :min and :max", Ticket.class)
                    .setParameter("min", min)
                    .setParameter("max", max).list();
        }
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

    @Override
    public List<Ticket> findByAttractionName(String attractionName) {
        try (Session session = HibernateSession.getSessionFactory().openSession()) {
            return session.createQuery("from Ticket where attractionName = :attractionName", Ticket.class)
                    .setParameter("attractionName", attractionName).list();
        }
    }

}