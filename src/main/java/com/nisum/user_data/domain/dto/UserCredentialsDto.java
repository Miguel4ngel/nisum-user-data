package com.nisum.user_data.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDto {
    private String email;
    private String password;
}
