package com.nisum.user_data.domain.mapper;

import com.nisum.user_data.data.entity.phone.Phone;
import com.nisum.user_data.presentation.dto.CreateUserRequestDto;
import com.nisum.user_data.presentation.dto.CreateUserResponseDto;
import com.nisum.user_data.presentation.dto.PhoneDto;
import lombok.extern.log4j.Log4j2;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */
@Log4j2
public class PhoneMapper {

    public static Phone fromPhoneDtoToEntity(PhoneDto phoneDto) {
        log.info("Mapping Phone Request to new Entity");
        return Phone.builder()
                .number(phoneDto.getNumber())
                .cityCode(phoneDto.getCityCode())
                .countryCode(phoneDto.getCountryCode())
                .build();
    }

    public static PhoneDto fromPhoneEntityToDto(Phone phoneEntity) {
        log.info("Mapping Phone Entity to new DTO");
        return PhoneDto.builder()
                .number(phoneEntity.getNumber())
                .cityCode(phoneEntity.getCityCode())
                .countryCode(phoneEntity.getCountryCode())
                .build();
    }

}
