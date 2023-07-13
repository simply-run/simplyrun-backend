package com.simpllyrun.srcservice.api.user.dto.mapper;

import com.simpllyrun.srcservice.api.user.domain.Agreement;
import com.simpllyrun.srcservice.api.user.dto.AgreementDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgreementDtoMapper {
    AgreementDtoMapper INSTANCE = Mappers.getMapper(AgreementDtoMapper.class);

    @Mapping(target = "isRequired", source = "required")
    AgreementDto toDto(Agreement agreement);

    List<AgreementDto> toDtos(List<Agreement> agreements);
}
