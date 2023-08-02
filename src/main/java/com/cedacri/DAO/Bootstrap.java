package com.cedacri.DAO;

import com.cedacri.model.Address;
import com.cedacri.model.Course;
import com.cedacri.model.CourseMaterial;
import com.cedacri.model.Gender;
import com.cedacri.model.Student;
import com.cedacri.model.Teacher;
import com.cedacri.service.JpaService;
import java.time.LocalDate;

public class Bootstrap {
  JpaService jpaService = JpaService.getInstance();

  public Bootstrap() {

    Student tom  = new Student("tom", "smith", LocalDate.now().toString()
    , Gender.MALE, new Address("1street", "houseNr1", "chisinau1"));

    jpaService.runInTransaction(entityManager -> {
        entityManager.persist(tom);
        return null;
    });

    Student jacky  = new Student("jacky", "chan", LocalDate.now().toString()
        , Gender.FEMALE, new Address("2street", "houseNr2", "chisinau2"));

    jpaService.runInTransaction(entityManager -> {
      entityManager.persist(jacky);
      return null;
    });

    Teacher teacherSam = new Teacher("Sam", "teacher");
    jpaService.runInTransaction(entityManager -> {
      entityManager.persist(teacherSam);
      return null;
    });

    CourseMaterial javaCourseMaterial = new CourseMaterial("java site:google.com");
    CourseMaterial hibernateCourseMaterial = new CourseMaterial("hibernate site:google.com");

    Course hibernateCourse = new Course("hibernate");
      hibernateCourse.addTeacher(teacherSam);
      hibernateCourse.addCourseMaterial(hibernateCourseMaterial);
      hibernateCourse.addStudent(jacky);
      hibernateCourse.addStudent(tom);

      jpaService.runInTransaction(entityManager -> {
        entityManager.persist(hibernateCourse);
        return null;
      });

    Course javaCourse = new Course("java");
    javaCourse.addTeacher(teacherSam);
    javaCourse.addCourseMaterial(javaCourseMaterial);
    javaCourse.addStudent(tom);

      jpaService.runInTransaction(entityManager -> {
        entityManager.persist(javaCourse);
        return null;
      });

//    try {
//      jpaService.runInTransaction(entityManager -> {
//        entityManager.persist(teacher);
//        return null;
//      });
//
//    }finally {
//      jpaService.shutdown();
//    }
  }
}
