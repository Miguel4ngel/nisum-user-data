package com.ey.user_data.domain.mapper;

import com.ey.user_data.data.entity.user.User;
import com.ey.user_data.presentation.dto.user.CreateUserRequest;
import com.ey.user_data.presentation.dto.user.CreateUserResponse;
import com.ey.user_data.presentation.dto.user.FindUserResponse;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */
@Log4j2
public class UserMapper {

    public static User fromUserDtoToEntity(CreateUserRequest userDto, String jwt) {
        log.info("Mapping User Request to new Entity");
        LocalDateTime creationDate = LocalDateTime.now();
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .createdAt(creationDate)
                .modifiedAt(creationDate)
                .lastLogin(creationDate)
                .isActive(true)
                .role("USER")
                .jwtAccessToken(jwt)
                .build();
    }

    public static CreateUserResponse fromUserEntityToDto(User userEntity) {
        log.info("Mapping User Entity to new DTO");
        return CreateUserResponse.builder()
                .userId(UUID.fromString(userEntity.getUserId()))
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .createdAt(userEntity.getCreatedAt())
                .modifiedAt(userEntity.getModifiedAt())
                .lastLogin(userEntity.getLastLogin().toLocalDate())
                .isActive(userEntity.isActive())
                .jwtAccessToken(userEntity.getJwtAccessToken())
                .role(userEntity.getRole())
                .build();
    }

    public static FindUserResponse fromUserEntityToFindUserDto(User userEntity) {
        log.info("Mapping User Entity to new DTO");
        return FindUserResponse.builder()
                .userId(UUID.fromString(userEntity.getUserId()))
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .createdAt(userEntity.getCreatedAt())
                .modifiedAt(userEntity.getModifiedAt())
                .lastLogin(userEntity.getLastLogin())
                .isActive(userEntity.isActive())
                .role(userEntity.getRole())
                .build();
    }
}
