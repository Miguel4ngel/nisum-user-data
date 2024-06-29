package com.nisum.user_data.domain.mapper;

import com.nisum.user_data.data.entity.user.User;
import com.nisum.user_data.presentation.dto.user.CreateUserRequestDto;
import com.nisum.user_data.presentation.dto.user.CreateUserResponseDto;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */
@Log4j2
public class UserMapper {

    public static User fromUserDtoToEntity(CreateUserRequestDto userDto, String jwt) {
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
                .jwtAccessToken(jwt)
                .build();
    }

    public static CreateUserResponseDto fromUserEntityToDto(User userEntity) {
        log.info("Mapping User Entity to new DTO");
        return CreateUserResponseDto.builder()
                .userId(UUID.fromString(userEntity.getUserId()))
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .createdAt(userEntity.getCreatedAt())
                .modifiedAt(userEntity.getModifiedAt())
                .lastLogin(userEntity.getLastLogin().toLocalDate())
                .isActive(userEntity.isActive())
                .jwtAccessToken(userEntity.getJwtAccessToken())
                .build();
    }
}
