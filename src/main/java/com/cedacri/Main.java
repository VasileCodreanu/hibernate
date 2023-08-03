package com.cedacri;

public class Main {

  public static void main(String[] args) {

      System.out.printf("Hello and welcome!");

      //
//    new Bootstrap();



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

    //Query query = session.createQuery("""
//    select m
//    from Model m
//    join fetch m.modelType
//    where modelGroup.id = :modelGroupId
//    """
//);
  }
}
