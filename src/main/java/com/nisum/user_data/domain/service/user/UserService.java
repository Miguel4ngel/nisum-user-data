package com.nisum.user_data.domain.service.user;

import com.nisum.user_data.data.entity.phone.Phone;
import com.nisum.user_data.data.entity.user.User;
import com.nisum.user_data.data.repository.phone.PhoneRepository;
import com.nisum.user_data.data.repository.user.UserRepository;
import com.nisum.user_data.domain.exception.UserCreationException;
import com.nisum.user_data.domain.mapper.PhoneMapper;
import com.nisum.user_data.domain.mapper.UserMapper;
import com.nisum.user_data.domain.service.jwt.JwtUtil;
import com.nisum.user_data.presentation.dto.CreateUserRequestDto;
import com.nisum.user_data.presentation.dto.CreateUserResponseDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Service
@Log4j2
public class UserService {

    private PhoneRepository phoneRepository;

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       PhoneRepository phoneRepository,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.jwtUtil = jwtUtil;
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
        return UserMapper.fromUserEntityToDto(userRepository.findByEmail(newUser.getEmail()).get());
    }


}
