package com.nisum.user_data.presentation.dto.user;

import jakarta.validation.constraints.Email;
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
public class CreateUserRequestDto {

    private String name;

    @Email(regexp = ".+[@].+[\\.].+", message = "The email must have a valid format.")
    private String email;

    private String password;
    private List<PhoneDto> phones;
}
