package by.vsu.attendance.controllers;

import by.vsu.attendance.domain.UserRole;
import by.vsu.attendance.dto.AuthRequest;
import by.vsu.attendance.dto.AuthResponse;
import by.vsu.attendance.dto.RegisterRequest;
import by.vsu.attendance.services.AuthService;
import by.vsu.attendance.services.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return authService.login(authRequest);
    }

    @GetMapping("/role")
    public UserRole getUserRole(
            @RequestHeader("Authorization") @NotEmpty String authHeader
    ) {
        if (!authHeader.startsWith(jwtPrefix)) {
            throw new IllegalArgumentException();
        }
        final String jwt = authHeader.substring(jwtPrefix.length());

        String role = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));
        return UserRole.valueOf(role);
    }
}
