package com.ey.user_data.domain.service.user;

import com.ey.user_data.data.entity.phone.Phone;
import com.ey.user_data.data.entity.user.User;
import com.ey.user_data.data.repository.phone.PhoneRepository;
import com.ey.user_data.data.repository.user.UserRepository;
import com.ey.user_data.domain.exception.UserCreationException;
import com.ey.user_data.domain.exception.UserNotFoundException;
import com.ey.user_data.domain.mapper.PhoneMapper;
import com.ey.user_data.domain.mapper.UserMapper;
import com.ey.user_data.domain.service.jwt.JwtUtilService;
import com.ey.user_data.presentation.dto.user.CreateUserRequest;
import com.ey.user_data.presentation.dto.user.CreateUserResponse;
import com.ey.user_data.presentation.dto.user.FindUserResponse;
import com.ey.user_data.presentation.dto.user.UpdateUserRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public static final String ADMIN_EMAIL = "miguel.sanjuanm@gmail.com";

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
    /**
     * Creates a new user with the given data.
     * @param newUser
     * @return CreateUserResponseDto
     * @throws UserCreationException if the user with the given email already exists.
     */
    public CreateUserResponse createUser(CreateUserRequest newUser) {
        log.info("[createUser] Creating user");

        userRepository.findByEmail(newUser.getEmail()).ifPresent(existentUser -> {
            throw new UserCreationException("User with the given email already exists.");
        });

        String jwt = jwtUtil.generateToken(newUser.getEmail());

        User userToSave = UserMapper.fromUserDtoToEntity(newUser, jwt);
        userRepository.save(userToSave);

        newUser.getPhones().forEach(phone -> {
            log.info("[createUser] Creating Phone with number [{}] for new User with email [{}]",
                    phone.getNumber(), newUser.getEmail());
            Phone phoneToSave = PhoneMapper.fromPhoneDtoToEntity(phone);
            phoneToSave.setUser(userToSave);
            phoneRepository.save(phoneToSave);
        });
        log.info("[createUser] New user created with unique Id [{}]", userToSave.getUserId());
        return UserMapper.fromUserEntityToDto(userToSave);
    }

    /**
     * Gets the user with the given Id.
     * @param userId
     * @return User
     * @throws UserNotFoundException if the user with the given Id does not exist.
     */
    public FindUserResponse getUserById(UUID userId) {
        log.info("[getUserById] Getting user with Id [{}]", userId);
        return UserMapper.fromUserEntityToFindUserDto(userRepository.findByUserId(userId.toString())
                .orElseThrow(() -> new UserNotFoundException("User with Id [" + userId + "] not found.")));
    }

    /**
     * Gets all users.
     * @return List<User>
     */
    public List<FindUserResponse> getAllUsers() {
        log.info("[getAllUsers] Getting all users");
        List<FindUserResponse> userList = new ArrayList<>();
        List<User> foundUsers = userRepository.findAll();
        foundUsers.removeIf(user -> user.getEmail().equalsIgnoreCase(ADMIN_EMAIL));
        foundUsers.forEach(user -> {
            userList.add(UserMapper.fromUserEntityToFindUserDto(user));
        });
        log.info("[getAllUsers] [{}] users retrieved", userList.size());
        return userList;
    }

    /**
     * Updates the user with the given Id.
     * @param userId
     * @param newUserInformation
     * @throws UserNotFoundException if the user with the given Id does not exist.
     */
    public void updateUser(UUID userId, UpdateUserRequest newUserInformation) {
        log.info("[updateUser] Updating user with Id [{}]", userId);
        User user = userRepository.findByUserId(userId.toString())
                .orElseThrow(() -> new UserNotFoundException("User with Id [" + userId + "] not found."));
        user.setName((newUserInformation.getNewName() != null )
                ? newUserInformation.getNewName()
                : user.getName());
        user.setPassword((newUserInformation.getNewPassword() != null )
                ? newUserInformation.getNewPassword()
                : user.getPassword());
        user.setModifiedAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("[updateUser] User with Id [{}] updated", userId);
    }

    /**
     * Deletes the user with the given Id.
     * @param userId
     * @throws UserNotFoundException if the user with the given Id does not exist.
     */
    public void deleteUser(UUID userId) {
        log.info("[deleteUser] Deleting user with Id [{}]", userId);
        User user = userRepository.findByUserId(userId.toString())
                .orElseThrow(() -> new UserNotFoundException("User with Id [" + userId + "] not found."));
        userRepository.delete(user);
        log.info("[deleteUser] User with Id [{}] deleted", userId);
    }
}