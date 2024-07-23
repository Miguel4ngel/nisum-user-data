package com.ey.user_data.presentation.controller.user;

import com.ey.user_data.domain.service.user.UserService;
import com.ey.user_data.presentation.dto.error.ErrorDTO;
import com.ey.user_data.presentation.dto.user.CreateUserRequest;
import com.ey.user_data.presentation.dto.user.CreateUserResponse;
import com.ey.user_data.presentation.dto.user.FindUserResponse;
import com.ey.user_data.presentation.dto.user.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@RestController
@RequestMapping("/v1/user")
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
                        schema = @Schema(implementation = CreateUserResponse.class))}),
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
    public ResponseEntity<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }

    @GetMapping("/{userId}")
    @Operation(description = "Gets the user with the given Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FindUserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    public ResponseEntity<FindUserResponse> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/")
    @Operation(description = "Gets all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FindUserResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    public ResponseEntity<List<FindUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    @Operation(description = "Updates the user with the given Id. Only the user name and password can be updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FindUserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    public ResponseEntity<FindUserResponse> updateUser(@PathVariable UUID userId, @Valid @RequestBody UpdateUserRequest updatedUser) {
        userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    @Operation(description = "Deletes the user with the given Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted."),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
