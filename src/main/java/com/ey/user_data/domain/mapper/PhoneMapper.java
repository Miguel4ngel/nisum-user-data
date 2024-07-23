package com.ey.user_data.domain.mapper;

import com.ey.user_data.data.entity.phone.Phone;
import com.ey.user_data.presentation.dto.phone.PhoneDto;
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
