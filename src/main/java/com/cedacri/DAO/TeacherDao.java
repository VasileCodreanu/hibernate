package com.cedacri.DAO;

import com.cedacri.model.Teacher;
import java.util.List;
import java.util.Optional;

public class TeacherDao implements Dao<Teacher> {


  public TeacherDao() {

  }

  @Override
  public Optional<Teacher> getByID(long id) {
    return Optional.empty();
  }

  @Override
  public List<Teacher> getAll() {
    return null;
  }

  @Override
  public void save(Teacher teacher) {

  }

  @Override
  public void update(Teacher teacher, String[] params) {

  }

  @Override
  public void delete(Teacher teacher) {

  }
}
