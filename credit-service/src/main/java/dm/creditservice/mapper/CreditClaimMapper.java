package dm.creditservice.mapper;

import dm.creditservice.entity.CreditClaimEntity;
import dm.creditservice.payload.CreditClaimResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditClaimMapper {

    CreditClaimEntity toEntity(CreditClaimResponse creditClaimResponse);

    CreditClaimResponse toDto(CreditClaimEntity creditClaimEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CreditClaimEntity partialUpdate(CreditClaimResponse creditClaimResponse, @MappingTarget CreditClaimEntity creditClaimEntity);
}