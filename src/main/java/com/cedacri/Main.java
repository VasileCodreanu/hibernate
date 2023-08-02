package com.cedacri;

import com.cedacri.DAO.Bootstrap;
import com.cedacri.DAO.RelatedCourseAndCourseMaterial;
import com.cedacri.model.Course;
import com.cedacri.model.CourseMaterial;
import com.cedacri.model.Student;
import com.cedacri.service.JpaService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {

  public static void main(String[] args) {

      System.out.printf("Hello and welcome!");




//    new Bootstrap();

//Query query = session.createQuery("""
//    select m
//    from Model m
//    join fetch m.modelType
//    where modelGroup.id = :modelGroupId
//    """
//);

//        JpaService jpaService = JpaService.getInstance();
//
//        try {
//            jpaService.runInTransaction(entityManager -> {
//                entityManager.persist(tom);
//                return null;
//            });
//
//        }finally {
//            jpaService.shutdown();
//        }


      System.out.printf("End");

//        public void deleteById(Integer id) {
//            // Retrieve the movie with this ID
//            Movie movie = entityManager.find(Movie.class, id);
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

  }
}
