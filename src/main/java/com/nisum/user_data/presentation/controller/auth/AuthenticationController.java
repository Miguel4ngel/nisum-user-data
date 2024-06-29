package com.nisum.user_data.presentation.controller.auth;

import com.nisum.user_data.domain.service.jwt.JwtUtilService;
import com.nisum.user_data.presentation.dto.auth.AuthenticationRequestDto;
import com.nisum.user_data.presentation.dto.auth.AuthenticationResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(jwtUtilService.generateToken(authenticationRequestDto));
    }

}
