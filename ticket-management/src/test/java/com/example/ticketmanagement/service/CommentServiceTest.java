package com.example.ticketmanagement.service;

import com.example.ticketmanagement.dto.CommentDTO;
import com.example.ticketmanagement.model.Comment;
import com.example.ticketmanagement.model.Ticket;
import com.example.ticketmanagement.model.User;
import com.example.ticketmanagement.repository.CommentRepository;
import com.example.ticketmanagement.repository.TicketRepository;
import com.example.ticketmanagement.repository.UserRepository;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private User user;
    private Ticket ticket;
    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        ticket = new Ticket();
        ticket.setId(2L);

        comment = new Comment(1L, "Test Comment", ticket, user, LocalDateTime.now());
        commentDTO = new CommentDTO(1L, "Test Comment", LocalDateTime.now(), user.getId(), ticket.getId());

        // Folosim lenient() pentru a evita `UnnecessaryStubbingException`
        lenient().when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    }

    @Test
    void shouldCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO createdComment = commentService.createComment(commentDTO);

        assertEquals(commentDTO.getText(), createdComment.getText());
        assertEquals(commentDTO.getUserId(), createdComment.getUserId());
        assertEquals(commentDTO.getTicketId(), createdComment.getTicketId());

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void shouldGetCommentById() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        Optional<CommentDTO> retrievedComment = commentService.getCommentById(comment.getId());

        assertEquals(comment.getText(), retrievedComment.get().getText());
        assertEquals(comment.getUser().getId(), retrievedComment.get().getUserId());
        assertEquals(comment.getTicket().getId(), retrievedComment.get().getTicketId());

        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @Test
    void shouldUpdateComment() {
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO updatedDTO = new CommentDTO(comment.getId(), "Updated Comment", LocalDateTime.now(), user.getId(), ticket.getId());

        CommentDTO updatedComment = commentService.updateComment(comment.getId(), updatedDTO);

        assertEquals(updatedDTO.getText(), updatedComment.getText());

        verify(commentRepository, times(1)).findById(comment.getId());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void shouldDeleteComment() {
        doNothing().when(commentRepository).deleteById(comment.getId());

        commentService.deleteComment(comment.getId());

        verify(commentRepository, times(1)).deleteById(comment.getId());
    }
}
