package com.oscar.bookstoremonolith.service;

import com.oscar.bookstoremonolith.dto.UserCreateDTO;
import com.oscar.bookstoremonolith.dto.UserDTO;
import com.oscar.bookstoremonolith.entity.User;
import com.oscar.bookstoremonolith.mapper.UserMapper;
import com.oscar.bookstoremonolith.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> findAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public UserDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDto(user);
    }

    public User createUser(UserCreateDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

}
