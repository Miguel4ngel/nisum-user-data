package com.nisum.user_data.presentation.controller;

import com.nisum.user_data.domain.service.user.UserService;
import com.nisum.user_data.presentation.dto.CreateUserRequestDto;
import com.nisum.user_data.presentation.dto.CreateUserResponseDto;
import com.nisum.user_data.presentation.dto.error.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Controller to perform CRUD Operations on User Entity.")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/")
    @Operation(description = "Creates a new User with the recieved parameters. All Users are created with an active status ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created.",
                    content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = CreateUserResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "User not created due to an service internal error.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class),
                            examples = {@ExampleObject(value =
                                    "{\"message\": \"Cannot create new User.\", \"timestamp\": \"2024-02-09T12:00:00\"}")})}),
            @ApiResponse(responseCode = "409", description = "User not created. Email is already registered.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class),
                            examples = {@ExampleObject(value =
                                    "{\"message\": \"Cannot create new User.\", \"timestamp\": \"2024-02-09T12:00:00\"}")})})
    })
    public ResponseEntity<CreateUserResponseDto> create(@Valid @RequestBody CreateUserRequestDto newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }
}
