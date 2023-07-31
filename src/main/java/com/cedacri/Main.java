package com.cedacri;

import com.cedacri.DAO.Bootstrap;
import com.cedacri.model.Course;
import com.cedacri.model.Student;
import com.cedacri.service.JpaService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello world!");

    new Bootstrap();
//
//
//    Student tom = new Student("tom", "smith", LocalDate.now().toString());
//    Student jack = new Student("jack", "chan", LocalDate.now().toString() );
//
//    Course sql  = new Course( "sql" );
//    Course java = new Course( "java" );
//
//    Set<Student> studentList = new HashSet<>();
//    studentList.add(tom);
//    studentList.add(jack);
//    Set<Course> courseList = new HashSet<>();
//    courseList.add(sql);
//    courseList.add(java);
//
////        Student studenta = new Student("tim", "sth", LocalDate.now().toString(), courseList );
//
//    JpaService jpaService = JpaService.getInstance();
//
//    try {
//      jpaService.runInTransaction(entityManager -> {
//        entityManager.persist(tom);
//        return null;
//      });
//
//    }finally {
//      jpaService.shutdown();
//    }
//







//
//        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-mysql")) {
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            EntityTransaction transaction = entityManager.getTransaction();
//            transaction.begin();
//
//                entityManager.persist(studenta);//add studenta to Entity manager context
//
//            transaction.commit();//
//        }


    System.out.printf("End");

    //@OneToMany
    //@JoinColumn(name = "TEACHER_ID", referencedColumnName = "ID")
    //private List<Course> courses;
    //Using this annotation will tell JPA that the COURSE table must have
    // a foreign key column TEACHER_ID that references the TEACHER table's ID column.
    //It's a good practice to put the owning side of a relationship in the
    // class/table where the foreign key will be held.

    //@ManyToMany
    //@JoinTable(
    //  name = "STUDENTS_COURSES",
    //  joinColumns = @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID"),
    //  inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    //)
    //private List<Student> students;
    // joinColumns defines how to configure the join column (foreign key) of the owning side of
    // the relationship in the table. In this case, the owning side is a Course.
    //inverseJoinColumns parameter does the same, but for the referencing side (Student).

  }
}
