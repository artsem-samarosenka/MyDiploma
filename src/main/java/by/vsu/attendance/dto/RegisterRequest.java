package by.vsu.attendance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {

    private @NotEmpty String username;
    private @NotEmpty String password;
}
