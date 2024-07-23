package com.ey.user_data.presentation.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String newName;
    @Size(min = 10, message = "Password must be at least 10 characters long")
    private String newPassword;
}
