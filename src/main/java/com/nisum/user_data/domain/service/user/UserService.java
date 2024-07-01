package com.nisum.user_data.domain.service.user;

import com.nisum.user_data.data.entity.phone.Phone;
import com.nisum.user_data.data.entity.user.User;
import com.nisum.user_data.data.repository.phone.PhoneRepository;
import com.nisum.user_data.data.repository.user.UserRepository;
import com.nisum.user_data.domain.exception.UserCreationException;
import com.nisum.user_data.domain.mapper.PhoneMapper;
import com.nisum.user_data.domain.mapper.UserMapper;
import com.nisum.user_data.domain.service.jwt.JwtUtilService;
import com.nisum.user_data.presentation.dto.user.CreateUserRequestDto;
import com.nisum.user_data.presentation.dto.user.CreateUserResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Service
@Log4j2
public class UserService {

    private PhoneRepository phoneRepository;

    private UserRepository userRepository;

    private JwtUtilService jwtUtil;

    public UserService(UserRepository userRepository,
                       PhoneRepository phoneRepository,
                       JwtUtilService jwtUtil) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    public void initAdminUser() {
        log.info("Creating admin user");
        User adminUser = User.builder()
                .email("miguel.sanjuanm@gmail.com")
                .password("admin")
                .isActive(true)
                .role("ADMIN")
                .userId(UUID.randomUUID().toString())
                .name("Miguel Angel")
                .build();
        userRepository.save(adminUser);
        log.info("Admin user created");
    }

    public CreateUserResponseDto createUser(CreateUserRequestDto newUser) {
        log.info("Creating user");

        userRepository.findByEmail(newUser.getEmail()).ifPresent(existentUser -> {
            throw new UserCreationException("User with the given email already exists.");
        });

        String jwt = jwtUtil.generateToken(newUser.getEmail());

        User userToSave = UserMapper.fromUserDtoToEntity(newUser, jwt);
        userRepository.save(userToSave);

        newUser.getPhones().forEach(phone -> {
            log.info("Creating Phone with number [{}] for new User with email [{}]",
                    phone.getNumber(), newUser.getEmail());
            Phone phoneToSave = PhoneMapper.fromPhoneDtoToEntity(phone);
            phoneToSave.setUser(userToSave);
            phoneRepository.save(phoneToSave);
        });
        log.info("New user created with unique Id [{}]", userToSave.getUserId());
        return UserMapper.fromUserEntityToDto(userToSave);
    }
}