package org.example.controller;


import org.example.model.DTO.BaseDto;

import java.util.List;

public interface AbstractCrudController<D extends BaseDto, CD, UD, K> {
    D create(CD dto);

    D update(UD dto);

    D get(K id);

    List<D> getAll();

    void delete(K id);
}
