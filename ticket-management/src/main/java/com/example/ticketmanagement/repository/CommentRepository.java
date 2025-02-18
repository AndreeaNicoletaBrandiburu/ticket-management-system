package com.example.ticketmanagement.repository;

import com.example.ticketmanagement.model.Comment;
import com.example.ticketmanagement.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // ✅ Caută comentarii folosind entitatea Ticket, nu `ticketId`
    List<Comment> findByTicket(Ticket ticket);
}
