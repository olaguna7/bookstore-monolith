package com.oscar.bookstoremonolith.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscar.bookstoremonolith.dto.OrderDTO;
import com.oscar.bookstoremonolith.dto.UserCreateDTO;
import com.oscar.bookstoremonolith.dto.UserDTO;
import com.oscar.bookstoremonolith.dto.UserSummaryDTO;
import com.oscar.bookstoremonolith.entity.User;
import com.oscar.bookstoremonolith.service.OrderService;
import com.oscar.bookstoremonolith.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private OrderService orderService;

    @Test
    void testGetAllUsers() throws Exception {
        UserDTO user1 = new UserDTO(1L, "john_doe", "john@email.com");
        UserDTO user2 = new UserDTO(2L, "jane_doe", "jane@email.com");

        Pageable pageable = Pageable.ofSize(8).withPage(0);

        when(userService.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user1, user2)));

        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "8")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].username").value("john_doe"))
                .andExpect(jsonPath("$.data.content[1].username").value("jane_doe"));
    }

    @Test
    void testGetUserById() throws Exception {
        UserDTO user = new UserDTO(1L, "john_doe", "john@doe.com");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("john_doe"))
                .andExpect(jsonPath("$.data.email").value("john@doe.com"));
    }

    @Test
    void testGetUserByUsername() throws Exception {
        UserDTO user = new UserDTO(1L, "john_doe", "john@doe.com");

        when(userService.findByUsername("john_doe")).thenReturn(user);

        mockMvc.perform(get("/users/by-username/john_doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("john_doe"))
                .andExpect(jsonPath("$.data.email").value("john@doe.com"));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        UserDTO user = new UserDTO(1L, "john_doe", "john@doe.com");

        when(userService.findByEmail("john@doe.com")).thenReturn(user);

        mockMvc.perform(get("/users/by-email/john@doe.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("john_doe"))
                .andExpect(jsonPath("$.data.email").value("john@doe.com"));
    }

    @Test
    void testGetUserOrders() throws Exception {
        UserSummaryDTO userDTO = new UserSummaryDTO(1L, "john_doe");
        OrderDTO order1 = new OrderDTO();
        order1.setOrderId(1L);
        order1.setUser(userDTO);
        OrderDTO order2 = new OrderDTO();
        order2.setOrderId(2L);
        order2.setUser(userDTO);

        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(orderService.findOrdersByUserId(1L, pageable)).thenReturn(new PageImpl<>(List.of(order1, order2)));

        mockMvc.perform(get("/users/1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].orderId").value(1L))
                .andExpect(jsonPath("$.data.content[1].orderId").value(2L));
    }

    @Test
    void testCreateUser() throws Exception {
        UserCreateDTO userToCreate = new UserCreateDTO("john_doe", "john@doe.com", "password");
        UserDTO user = new UserDTO(1L, "john_doe", "john@doe.com");
        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToCreate))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username").value("john_doe"))
                .andExpect(jsonPath("$.data.email").value("john@doe.com"))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("User created"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreateUserWithInvalidEmail() throws Exception {
        UserCreateDTO userToCreate = new UserCreateDTO("john_doe", "invalid-email", "password");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToCreate))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserWithNullField() throws Exception {
        UserCreateDTO userToCreate = new UserCreateDTO(null, "email@email.com", "password");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToCreate))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error: must not be null"));
    }
}
