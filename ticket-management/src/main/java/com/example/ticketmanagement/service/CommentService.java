package com.example.ticketmanagement.service;

import com.example.ticketmanagement.dto.CommentDTO;
import com.example.ticketmanagement.model.Comment;
import com.example.ticketmanagement.model.Ticket;
import com.example.ticketmanagement.model.User;
import com.example.ticketmanagement.repository.CommentRepository;
import com.example.ticketmanagement.repository.TicketRepository;
import com.example.ticketmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Creare comentariu
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    // ✅ Obține comentariu după ID
    public Optional<CommentDTO> getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(this::mapToDTO);
    }

    // ✅ Obține toate comentariile
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Obține comentarii pentru un anumit ticket
    public List<CommentDTO> getCommentsByTicketId(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticketul nu a fost găsit."));

        List<Comment> comments = commentRepository.findByTicket(ticket);
        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Actualizare comentariu
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentariul nu a fost găsit."));

        // ✅ Actualizare doar dacă sunt furnizate date noi
        if (commentDTO.getText() != null) {
            existingComment.setText(commentDTO.getText());
        }
        if (commentDTO.getCreatedAt() != null) {
            existingComment.setCreatedAt(commentDTO.getCreatedAt());
        }

        // ✅ Salvează modificările
        Comment updatedComment = commentRepository.save(existingComment);
        return mapToDTO(updatedComment);
    }

    // ✅ Ștergere comentariu
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // 🔹 Conversie Model → DTO
    private CommentDTO mapToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getUser() != null ? comment.getUser().getId() : null,
                comment.getTicket() != null ? comment.getTicket().getId() : null
        );
    }

    // 🔹 Conversie DTO → Model
    private Comment mapToEntity(CommentDTO commentDTO) {
        Ticket ticket = ticketRepository.findById(commentDTO.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticketul nu a fost găsit."));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit."));

        return new Comment(
                commentDTO.getId(),
                commentDTO.getText(),
                ticket,
                user,
                commentDTO.getCreatedAt() != null ? commentDTO.getCreatedAt() : LocalDateTime.now()
        );
    }
}
