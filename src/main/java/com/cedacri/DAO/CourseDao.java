package com.cedacri.DAO;

import com.cedacri.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CourseDao implements Dao<Course>{
  private final EntityManager em;

  public CourseDao (EntityManager em) {
    this.em = em;
  }

  @Override
  public Optional<Course> getById(Long id) {
    Course course = em.find(Course.class, id);
    em.detach(course);
    return course != null ? Optional.of(course) : Optional.empty();
  }

  @Override
  public Set<Course> getAll() {
    TypedQuery<Course> query = em.createQuery("SELECT c from Course c", Course.class);
    List<Course> courses = query.getResultList();
    return Set.copyOf(courses);
  }

  @Override
  public Optional<Course> save(Course course) {
    em.getTransaction().begin();
    em.persist(course);
//        em.flush();
    em.getTransaction().commit();
    return Optional.of(em.find(Course.class, course.getId()));
  }


  @Override
  public Optional<Course> update(Course course) {
    em.getTransaction().begin();
    em.merge(course);
    Course foundCourse = em.find(Course.class, course.getId());
//        em.flush();
    em.getTransaction().commit();
    return Optional.of(foundCourse);
  }

  @Override
  public void delete(Course course) {
    em.getTransaction().begin();
    em.remove(course);
//        em.flush();
    em.getTransaction().commit();
  }
}
