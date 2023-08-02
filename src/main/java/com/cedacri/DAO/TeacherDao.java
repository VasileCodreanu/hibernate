package com.cedacri.DAO;

import com.cedacri.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TeacherDao implements Dao<Teacher> {
  private final EntityManager em;

  public TeacherDao(EntityManager em) {
    this.em = em;
  }

  @Override
  public Optional<Teacher> getById(Long id) {
    em.getTransaction().begin();
    Teacher teacher = em.find(Teacher.class, id);

    em.detach(teacher);
    em.flush();
    em.getTransaction().commit();
    return teacher != null ? Optional.of(teacher) : Optional.empty();
  }

  @Override
  public Set<Teacher> getAll() {
    em.getTransaction().begin();
    TypedQuery<Teacher> query = em.createQuery("SELECT t from Teacher t", Teacher.class);
    List<Teacher> teachers = query.getResultList();
    em.flush();
    em.getTransaction().commit();
    return Set.copyOf(teachers);
  }


  @Override
  public Optional<Teacher> save(Teacher teacher) {
    em.getTransaction().begin();
    em.persist(teacher);
    em.flush();
    em.getTransaction().commit();
    return Optional.of( em.find(Teacher.class, teacher.getId()) );
  }

  @Override
  public Optional<Teacher> update(Teacher teacher) {
    em.getTransaction().begin();
    em.merge(teacher);
    Teacher foundTeacher = em.find(Teacher.class, teacher.getId());
    em.flush();
    em.getTransaction().commit();
    return Optional.of(foundTeacher);
  }

  @Override
  public void delete(Teacher teacher) {
    em.getTransaction().begin();
    em.remove(teacher);
    em.flush();
    em.getTransaction().commit();
  }

  public Optional<List<Teacher>> findByName(String name) {
      List<Teacher> teachersByName = em.createQuery("SELECT t FROM Teacher t WHERE t.firstName = :teacher_name", Teacher.class)
              .setParameter("teacher_name", name)
              .getResultList();
      return teachersByName != null ? Optional.of(teachersByName) : Optional.empty();
  }
}
