package by.vsu.attendance.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    private @NotBlank String username;
    private @NotBlank String password;
}
