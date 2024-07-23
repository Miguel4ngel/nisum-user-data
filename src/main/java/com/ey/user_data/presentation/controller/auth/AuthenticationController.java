package com.ey.user_data.presentation.controller.auth;

import com.ey.user_data.domain.service.jwt.JwtUtilService;
import com.ey.user_data.presentation.dto.auth.AuthenticationRequestDto;
import com.ey.user_data.presentation.dto.auth.AuthenticationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Allows a registered user to obtain a JWT Token.")
public class AuthenticationController {

    private JwtUtilService jwtUtilService;

    public AuthenticationController(JwtUtilService jwtUtilService) {
        this.jwtUtilService = jwtUtilService;
    }

    @PostMapping("/login")
    @Operation(description = "Allows a registered user to obtain a JWT Token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT Token generated.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid input parameters.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(jwtUtilService.generateToken(authenticationRequestDto));
    }

}
