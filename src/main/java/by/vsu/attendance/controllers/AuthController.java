package by.vsu.attendance.controllers;

import by.vsu.attendance.dto.AuthRequest;
import by.vsu.attendance.dto.AuthResponse;
import by.vsu.attendance.dto.RegisterRequest;
import by.vsu.attendance.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest authRequest
    ) {
        return authService.login(authRequest);
    }
}
