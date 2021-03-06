package com.application.airport_app.service;

import java.util.List;

public interface BaseService<T> {

    void save(T item);

    void update(Long id, T item);

    void delete(Long id);

    T getById(Long id);

    List<T> list();
}
