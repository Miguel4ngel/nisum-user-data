package com.nisum.user_data.domain.service.user;

import com.nisum.user_data.data.entity.user.User;
import com.nisum.user_data.data.repository.phone.PhoneRepository;
import com.nisum.user_data.data.repository.user.UserRepository;
import com.nisum.user_data.domain.exception.UserCreationException;
import com.nisum.user_data.domain.service.jwt.JwtUtilService;
import com.nisum.user_data.presentation.dto.user.CreateUserRequestDto;
import com.nisum.user_data.presentation.dto.user.CreateUserResponseDto;
import com.nisum.user_data.presentation.dto.user.PhoneDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private JwtUtilService jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create user successfully")
    void createUser_useCase1() {
        // Arrange
        CreateUserRequestDto newUser = CreateUserRequestDto.builder()
                .phones(Collections.singletonList(PhoneDto.builder().build()))
                .build();
        User userToSave = User.builder().build();
        newUser.setEmail("test@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(anyString())).thenReturn("token");
        when(userRepository.save(userToSave)).thenAnswer(i -> i.getArguments()[0]);

        // Act
        CreateUserResponseDto result = userService.createUser(newUser);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(jwtUtil, times(1)).generateToken(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when user already exists")
    void createUser_useCase2() {
        // Arrange
        CreateUserRequestDto newUser = CreateUserRequestDto.builder().build();
        newUser.setEmail("test@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserCreationException.class, () -> userService.createUser(newUser));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Should throw exception when token creation fails")
    void createUser_useCase3() {
        // Arrange
        CreateUserRequestDto newUser = CreateUserRequestDto.builder().build();
        newUser.setEmail("test@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(anyString())).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(newUser));
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(jwtUtil, times(1)).generateToken(anyString());
    }

}
