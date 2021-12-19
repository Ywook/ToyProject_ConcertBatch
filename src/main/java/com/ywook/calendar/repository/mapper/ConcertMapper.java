package com.ywook.calendar.repository.mapper;

import com.ywook.calendar.domain.dto.base.ConcertBaseDto;
import com.ywook.calendar.domain.entity.ConcertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

//https://mapstruct.org/
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE, // 소스 매핑 안한게 있으면 무시
        unmappedTargetPolicy = ReportingPolicy.IGNORE // 타겟에 매핑 안한게 있으면 무시
)
public interface ConcertMapper extends EntityMapper<ConcertBaseDto, ConcertEntity>{

    @Override
    ConcertBaseDto toDto(ConcertEntity entity);

    @Override
    ConcertEntity toEntity(ConcertBaseDto dto);

}
