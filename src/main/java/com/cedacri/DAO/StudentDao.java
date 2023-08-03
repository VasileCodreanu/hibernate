package com.cedacri.DAO;

import com.cedacri.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StudentDao implements Dao<Student>{

  private final EntityManager em;

  public StudentDao(EntityManager em) {
    this.em = em;
  }

  @Override
  public Optional<Student> getById(Long id) {
    Student student = em.find(Student.class, id);
    return student != null ? Optional.of(student) : Optional.empty();
  }

  @Override
  public Set<Student> getAll() {
    TypedQuery<Student> query = em.createQuery("SELECT s from Student s", Student.class);
    List<Student> students = query.getResultList();
    return Set.copyOf(students);
  }

  @Override
  public Optional<Student> save(Student student) {
    em.getTransaction().begin();
    em.persist(student);
//        em.flush();
    em.getTransaction().commit();
    return Optional.of(em.find(Student.class, student.getId()));
  }

  @Override
  public Optional<Student> update(Student student) {
    em.getTransaction().begin();
    em.merge(student);
    Student foundStudent = em.find(Student.class, student.getId());
//        em.flush();
    em.getTransaction().commit();
    return Optional.of(foundStudent);

  }

  @Override
  public void delete(Student student) {
    em.getTransaction().begin();
    em.remove(student);
//        em.flush();
    em.getTransaction().commit();
  }

}
