package com.utad;


import com.utad.dao.imp.TicketDAOImpl;
import com.utad.dao.imp.UserDAOImpl;
import com.utad.entity.Ticket;
import com.utad.entity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        TicketDAOImpl ticketDAO = new TicketDAOImpl();

        User user = new User();
        user.setName("Jorge Mochon");
        userDAO.save(user);
        System.out.println("Usuario creado: " + user);

        Ticket ticket1 = new Ticket();
        ticket1.setAttractionName("Ticket descuento corte inglés");
        ticket1.setPrice(500.0);
        ticket1.setUser(user);
        ticketDAO.save(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setAttractionName("Ticket descuento mercadona");
        ticket2.setPrice(30.0);
        ticket2.setUser(user);
        ticketDAO.save(ticket2);

        System.out.println("Tickets creados para el usuario: " + user);
        List<Ticket> userTickets = ticketDAO.findByUserId(user.getId());
        userTickets.forEach(System.out::println);

        Double averageSpending = ticketDAO.getAverageSpendingByUser(user.getId());
        System.out.println("Gasto medio de Jorge Mochón: " + averageSpending);

        System.out.println("Entradas para la atracción 'Ticket descuento corte inglés':");
        List<Ticket> attractionTickets = ticketDAO.findByAttractionName("Ticket descuento corte inglés");
        attractionTickets.forEach(System.out::println);

        System.out.println("Entradas con precios entre 25 y 60:");
        List<Ticket> ticketsInRange = ticketDAO.findByPriceRange(25.0, 60.0);
        ticketsInRange.forEach(System.out::println);

        user.setName("Coco");
        userDAO.update(user);
        System.out.println("Usuario actualizado: " + userDAO.findById(user.getId()));

        long ticketCount = userTickets.size();
        System.out.println("Cantidad total de tickets del usuario: " + ticketCount);

        System.out.println("Todos los usuarios en la base de datos:");
        List<User> allUsers = userDAO.findAll();
        allUsers.forEach(System.out::println);

        ticketDAO.delete(ticket1);
        System.out.println("Ticket eliminado: " + ticket1);

        userDAO.delete(user);
        System.out.println("Usuario eliminado. " + user);
    }
}
