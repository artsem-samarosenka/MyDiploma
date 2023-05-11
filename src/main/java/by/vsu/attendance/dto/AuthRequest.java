package by.vsu.attendance.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {

    private @NotEmpty String username;
    private @NotEmpty String password;
}
