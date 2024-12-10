package com.utad.dao.interfaces;

import com.utad.entity.Ticket;

import java.util.List;

public interface TicketDAO {
    void save(Ticket ticket);
    Ticket findById(Long id);
    List<Ticket> findAll();
    void update(Ticket ticket);
    void delete(Ticket ticket);
    List<Ticket> findByUserId(Long userId);
    Double getAverageSpendingByUser(Long userId);
    List<Ticket> findByPriceRange(double min, double max);
    List<Ticket> findByAttractionName(String attractionName);
}