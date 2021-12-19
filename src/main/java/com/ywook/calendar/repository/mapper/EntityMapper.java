package com.ywook.calendar.repository.mapper;

import org.mapstruct.factory.Mappers;

public interface EntityMapper <D, E>{
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    E toEntity(D dto);
    D toDto(E entity);
}
