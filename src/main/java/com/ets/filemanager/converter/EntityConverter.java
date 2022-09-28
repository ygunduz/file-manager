package com.ets.filemanager.converter;

import java.util.List;

public interface EntityConverter <E, D> {
    E toEntity(D dto);
    D toDto(E entity);

    default List<D> toDto(List<E> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
