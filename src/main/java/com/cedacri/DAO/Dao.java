package com.cedacri.DAO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Dao <T> {
  Optional<T> getById(Long id);

  Set<T> getAll();

  Optional<T> save(T t);

  Optional<T> update(T t);

  void delete(T t);

}
