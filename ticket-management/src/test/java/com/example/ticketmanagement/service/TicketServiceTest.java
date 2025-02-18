package com.example.ticketmanagement.service;

import com.example.ticketmanagement.dto.TicketDTO;
import com.example.ticketmanagement.model.Category;
import com.example.ticketmanagement.model.Ticket;
import com.example.ticketmanagement.model.TicketStatus;
import com.example.ticketmanagement.model.User;
import com.example.ticketmanagement.repository.TicketRepository;
import com.example.ticketmanagement.repository.UserRepository;
import com.example.ticketmanagement.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketDTO ticketDTO;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        category = new Category();
        category.setId(2L);

        ticket = new Ticket(1L, "Test Ticket", "Description", TicketStatus.OPEN, LocalDateTime.now(), user, category);
        ticketDTO = new TicketDTO(1L, "Test Ticket", "Description", LocalDateTime.now(), TicketStatus.OPEN, user.getId(), category.getId());

        // Definim `when(...)` înainte să fie folosit în metodele de testare
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
    }

    @Test
    void shouldCreateTicket() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDTO createdTicket = ticketService.createTicket(ticketDTO);

        assertEquals(ticketDTO.getTitle(), createdTicket.getTitle());
        assertEquals(ticketDTO.getDescription(), createdTicket.getDescription());
        assertEquals(ticketDTO.getStatus(), createdTicket.getStatus());

        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}
