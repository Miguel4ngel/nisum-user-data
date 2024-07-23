package com.ey.user_data.presentation.dto.user;

import com.ey.user_data.presentation.dto.phone.PhoneDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotEmpty(message = "Name must not be empty")
    private String name;
    @Email(regexp = ".+[@].+[\\.].+", message = "The email must have a valid format.")
    private String email;
    @NotEmpty(message = "password must not be empty")
    private String password;
    @Valid
    @NotEmpty
    private List<PhoneDto> phones;
}
