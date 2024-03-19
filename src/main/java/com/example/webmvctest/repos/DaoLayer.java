package com.example.webmvctest.repos;

import java.util.List;
import java.util.Optional;

public interface DaoLayer<T> {

    public T add(T t);
    public T findById(int id);
    public T update(T t,int id);
    public void delete(int id);

    public List<T> findAll();

    T big();
}
