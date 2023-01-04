package com.huypq.web.admin.mapper;

import java.util.ArrayList;
import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntity(List<D> dtoList) {
        if ( dtoList == null ) {
            return null;
        }
        List<E> list = new ArrayList<>();
        for (D dto : dtoList) {
            list.add(toEntity(dto));
        }
        return list;
    }

    default List <D> toDto(List<E> entityList) {
        if ( entityList == null ) {
            return null;
        }
        List<D> list = new ArrayList<>();
        for (E entity : entityList) {
            list.add(toDto(entity));
        }
        return list;
    }
}
