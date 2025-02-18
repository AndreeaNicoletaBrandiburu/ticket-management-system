package com.example.ticketmanagement.service;

import com.example.ticketmanagement.dto.TicketDTO;
import com.example.ticketmanagement.model.Category;
import com.example.ticketmanagement.model.Ticket;
import com.example.ticketmanagement.model.TicketStatus;
import com.example.ticketmanagement.model.User;
import com.example.ticketmanagement.repository.TicketRepository;
import com.example.ticketmanagement.repository.UserRepository;
import com.example.ticketmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Creare ticket
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = mapToEntity(ticketDTO);
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToDTO(savedTicket);
    }

    // ✅ Obține ticket după ID
    public Optional<TicketDTO> getTicketById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(this::mapToDTO);
    }

    // ✅ Obține toate ticket-urile
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Actualizare ticket
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket-ul nu a fost găsit."));

        if (ticketDTO.getTitle() != null) {
            existingTicket.setTitle(ticketDTO.getTitle());
        }
        if (ticketDTO.getDescription() != null) {
            existingTicket.setDescription(ticketDTO.getDescription());
        }
        if (ticketDTO.getStatus() != null) {
            existingTicket.setStatus(ticketDTO.getStatus());
        }
        if (ticketDTO.getCreatedAt() != null) {
            existingTicket.setCreatedAt(ticketDTO.getCreatedAt());
        }

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return mapToDTO(updatedTicket);
    }

    // ✅ Ștergere ticket
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    // 🔹 Conversie Model → DTO
    private TicketDTO mapToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCreatedAt(),
                ticket.getStatus(),
                ticket.getUser() != null ? ticket.getUser().getId() : null, // ✅ Acces corect la ID-ul user-ului
                ticket.getCategory() != null ? ticket.getCategory().getId() : null // ✅ Acces corect la ID-ul categoriei
        );
    }

    // 🔹 Conversie DTO → Model
    private Ticket mapToEntity(TicketDTO ticketDTO) {
        User user = userRepository.findById(ticketDTO.getUserId()).orElse(null);
        Category category = categoryRepository.findById(ticketDTO.getCategoryId()).orElse(null);

        return new Ticket(
                ticketDTO.getId(),
                ticketDTO.getTitle(),
                ticketDTO.getDescription(),
                ticketDTO.getStatus(),
                LocalDateTime.now(),
                user, // ✅ Asignare corectă a utilizatorului
                category, // ✅ Asignare corectă a categoriei
                List.of() // ✅ Inițializare listă goală de comentarii, deoarece constructorul așteaptă o listă
        );
    }
}
