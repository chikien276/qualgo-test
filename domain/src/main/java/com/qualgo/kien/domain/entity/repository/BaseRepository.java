package com.qualgo.kien.domain.entity.repository;

public interface BaseRepository<T> {
  T findById(Long id);

  int update(T entity);

  int insert(T entity);
}
