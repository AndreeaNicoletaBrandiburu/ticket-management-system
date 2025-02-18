package com.example.ticketmanagement.controller;

import com.example.ticketmanagement.dto.UserDTO;
import com.example.ticketmanagement.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, "Andrei Popescu", "andrei@example.com");

        when(usersService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Andrei Popescu\", \"email\":\"andrei@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Andrei Popescu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("andrei@example.com"));

        verify(usersService, times(1)).createUser(any(UserDTO.class));
    }
}
