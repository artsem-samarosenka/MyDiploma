package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.UserRole;
import by.vsu.attendance.dto.AuthRequest;
import by.vsu.attendance.dto.AuthResponse;
import by.vsu.attendance.dto.RegisterRequest;
import by.vsu.attendance.services.AuthService;
import by.vsu.attendance.services.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Value("${application.jwt.prefix}")
    private String jwtPrefix;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody @Valid AuthRequest authRequest
    ) {
        log.debug("Processing GET request for /login with username = '{}'", authRequest.getUsername());
        AuthResponse authResponse = authService.login(authRequest);
        log.info("User '{}' logged successfully", authRequest.getUsername());
        return authResponse;
    }

    @GetMapping("/role")
    public UserRole getUserRole(
            @RequestHeader("Authorization") @NotBlank String authHeader
    ) {
        log.debug("Processing GET request for /role with Authorization header = '{}'", authHeader);
        final String role = jwtService.extractRoleByAuthHeader(authHeader);
        log.info("Returning '{}' Role for current User", role);
        return UserRole.valueOf(role);
    }
}
