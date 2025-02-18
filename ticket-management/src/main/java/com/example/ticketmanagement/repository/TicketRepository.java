package com.example.ticketmanagement.repository;

import com.example.ticketmanagement.model.Ticket;
import com.example.ticketmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // ✅ Corectat: căutare după `User`, nu după `userId`
    List<Ticket> findByUser(User user);
}
