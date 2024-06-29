package com.nisum.user_data.presentation.dto.user;

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
    private String number;
    private String cityCode;
    private String countryCode;
}
