package com.cedacri.DAO;

import com.cedacri.model.Course;
import com.cedacri.model.CourseMaterial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Optional;

public class RelatedCourseAndCourseMaterial {
  private final EntityManager em;
  private Dao<Course> courseDao;

  public RelatedCourseAndCourseMaterial(EntityManager em) {
    this.em = em;
    this.courseDao = new CourseDao(em);
  }

  public Optional<Course> saveCourseMaterial(Course course, CourseMaterial courseMaterial) {
//        course.setCourseMaterial(courseMaterial);
//        courseMaterial.setCourse(course);

    course.addCourseMaterial(courseMaterial);

    em.getTransaction().begin();
    em.persist(course);
//        em.flush();
    em.getTransaction().commit();
    return Optional.of(em.find(Course.class, course.getId()));
  }

  public void deleteCascadeCourseForCourseMaterial(Course course){
    em.getTransaction().begin();
    em.remove(course);
//        em.flush();
    em.getTransaction().commit();
  }

  public CourseMaterial getCourseMaterialByCourseId(Long courseId){
    Course courseById = em.find(Course.class, courseId);
    if (courseById != null){
      TypedQuery<CourseMaterial> query = em.createQuery(
              "SELECT c FROM CourseMaterial c where c.id=:id", CourseMaterial.class)
          .setParameter("id", courseId );
      return query.getSingleResult();
    }
    return new CourseMaterial();
  }
  //"select e.name, a.city from Employee e INNER JOIN e.address a"


//    public CourseMaterial updateCourseMaterialUrlByCourseId(Long courseId, String courserUrl){
//      Course courseById = em.find(Course.class, courseId);
//      TypedQuery<CourseMaterial> query = em.createQuery(
//              "SELECT c FROM CourseMaterial c where c.id=:id", CourseMaterial.class)
//          .setParameter("id", courseId );
//
//    }

  public void removeCourseMaterialForCourse(Long courseId) {
    Course courseById = em.find(Course.class, courseId);
    //remove cours material where courseId =?
//
//        if (courseById != null) {
//            try {
//                // Start a transaction because we're going to change the database
//                em.getTransaction().begin();
//
//                // Remove all references to this movie by superheroes
//                CourseMaterial courseMaterial = courseById.getCourseMaterial();
//                movie.getSuperHeroes().forEach(superHero -> {
//                    superHero.getMovies().remove(movie);
//                });
//
//                // Now remove the movie
//                entityManager.remove(movie);
//
//                // Commit the transaction
//                entityManager.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
  }
}

//    Movie movie = entityManager.find(Movie.class, id);
//            if (movie != null) {
//                try {
//                    // Start a transaction because we're going to change the database
//                    entityManager.getTransaction().begin();
//
//                    // Remove all references to this movie by superheroes
//                    movie.getSuperHeroes().forEach(superHero -> {
//                        superHero.getMovies().remove(movie);
//                    });
//
//                    // Now remove the movie
//                    entityManager.remove(movie);
//
//                    // Commit the transaction
//                    entityManager.getTransaction().commit();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

//}