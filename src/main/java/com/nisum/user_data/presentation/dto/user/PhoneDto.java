package com.nisum.user_data.presentation.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class PhoneDto {

    @Pattern(regexp = "\\d+", message = "Number must be numeric")
    @Size(min = 9, max = 9, message = "Number must be exactly 9 digits")
    private String number;

    @Pattern(regexp = "\\d+", message = "City code must be numeric")
    @Size(min = 2, max = 2, message = "City code must be exactly 2 digits")
    private String cityCode;

    @Pattern(regexp = "\\d+", message = "Country code must be numeric")
    @Size(min = 3, max = 3, message = "Country code must be exactly 3 digits")
    private String countryCode;
}
