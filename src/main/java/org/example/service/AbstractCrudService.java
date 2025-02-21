package org.example.service;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface AbstractCrudService<D,CD,UD,K,S> {
    void create(CD dto, S s);

    void update(UD dto, S s);

    D get(K id);

    List<D> getAll();

    void delete(K id);

    List<D> getById(K id);

}
