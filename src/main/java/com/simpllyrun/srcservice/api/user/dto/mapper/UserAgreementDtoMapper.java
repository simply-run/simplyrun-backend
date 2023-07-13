package com.simpllyrun.srcservice.api.user.dto.mapper;

import com.simpllyrun.srcservice.api.user.domain.UserAgreement;
import com.simpllyrun.srcservice.api.user.dto.UserAgreementDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserAgreementDtoMapper {
    UserAgreementDtoMapper INSTANCE = Mappers.getMapper(UserAgreementDtoMapper.class);

    @Mapping(source = "agreement.type", target = "agreementType")
    UserAgreementDto toDto(UserAgreement userAgreement);

    @Mapping(source = "agreementType", target = "agreement.type")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserAgreement toEntity(UserAgreementDto userAgreementDto);

    List<UserAgreementDto> toDtos(List<UserAgreement> userAgreements);

    List<UserAgreement> toEntities(List<UserAgreementDto> userAgreementDtos);
}
