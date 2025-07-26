package com.oscar.bookstoremonolith.service;

import com.oscar.bookstoremonolith.dto.UserCreateDTO;
import com.oscar.bookstoremonolith.dto.UserDTO;
import com.oscar.bookstoremonolith.entity.User;
import com.oscar.bookstoremonolith.exception.DuplicateEntityException;
import com.oscar.bookstoremonolith.mapper.UserMapper;
import com.oscar.bookstoremonolith.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findAll should return an empty array when there are no users in the database")
    void findAllShouldReturnEmptyList_whenThereAreNoUsers() {

        when(userRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<UserDTO> users = userService.findAll(pageable);

        assertThat(users.getSize()).isZero();

        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when user does not exist")
    void findByIdShouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findById(2L)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.findById(2L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("findByUsername should throw EntityNotFoundException when user does not exist")
    void findByUsernameShouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByUsername("userrrr")).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.findByUsername("userrrr"))
                .isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findByUsername("userrrr");
    }

    @Test
    @DisplayName("findByEmail should throw EntityNotFoundException when user does not exist")
    void findByEmailShouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByEmail("user@user.com")).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.findByEmail("user@user.com"))
                .isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findByEmail("user@user.com");
    }

    @Test
    @DisplayName("createUser should throw DuplicateEntityException when username already exists")
    void createUserShouldThrowException_whenUsernameAlreadyExists() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("user", "user@user.com", "12345678");

        when(userRepository.existsByUsername(userCreateDTO.getUsername())).thenThrow(DuplicateEntityException.class);

        assertThatThrownBy(() -> userService.createUser(userCreateDTO))
                .isInstanceOf(DuplicateEntityException.class);

        verify(userRepository, times(1)).existsByUsername("user");
    }

    @Test
    @DisplayName("createUser should throw DuplicateEntityException when email already exists")
    void createUserShouldThrowException_whenEmailAlreadyExists() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("user", "user@user.com", "12345678");

        when(userRepository.existsByEmail(userCreateDTO.getEmail())).thenThrow(DuplicateEntityException.class);

        assertThatThrownBy(() -> userService.createUser(userCreateDTO))
                .isInstanceOf(DuplicateEntityException.class);

        verify(userRepository, times(1)).existsByEmail(userCreateDTO.getEmail());
    }

    @Test
    @DisplayName("updateUser should throw EntityNotFoundException when user does not exist")
    void updateUserShouldThrowException_whenUserDoesNotExist() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("user", "user@user.com", "12345678");

        when(userRepository.findById(1L)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.updateUser(1L, userCreateDTO))
                .isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("deleteUser should throw EntityNotFoundException when user does not exist")
    void deleteUserShouldThrowException_whenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(userRepository, times(1)).existsById(1L);
    }

}
