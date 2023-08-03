package com.cedacri.DAO;

import static org.junit.jupiter.api.Assertions.*;

import com.cedacri.model.Course;
import com.cedacri.model.CourseMaterial;
import com.cedacri.service.JpaService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelatedCourseAndCourseMaterialTest {

  private RelatedCourseAndCourseMaterial dao;

  private static Course javaCourse;
  private static Course hibernateCourse;
  private CourseMaterial javaCourseMaterial;

  @BeforeEach
  void setUp() {
    EntityManager em = JpaService.getInstance().getTransaction();
    dao = new RelatedCourseAndCourseMaterial(em);

    javaCourse =  new Course("Java");
    hibernateCourse = new Course("Hibernate");
    javaCourseMaterial = new CourseMaterial(".com");
  }

  @Test
  void saveCourseMaterial() {
    Course savedJavaWithCourseMaterial = dao.saveCourseMaterial(javaCourse, javaCourseMaterial).get();
    assertAll(
        () -> assertNotNull(savedJavaWithCourseMaterial, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(savedJavaWithCourseMaterial.getId() > 0,
            "entity should return a valid DB id(id > 0);")
    );
  }

  @Test
  void deleteCascadeCourseForCourseMaterial(){
    Course savedJavaWithCourseMaterial = dao.saveCourseMaterial(javaCourse, javaCourseMaterial).get();
    dao.deleteCascadeCourseForCourseMaterial(savedJavaWithCourseMaterial);
  }

  @Test
  void updateCourseMaterialForCourseIdTest(){
    CourseMaterial courseMaterialByCourseId = dao.getCourseMaterialByCourseId(1L);

    assertAll(
        () -> assertNotNull(courseMaterialByCourseId, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(courseMaterialByCourseId.getId() > 0,
            "entity should return a valid DB id(id > 0);"),
        () -> assertEquals(1, courseMaterialByCourseId.getCourse().getId(), "getCourse().getId() should be: 1;")
    );
  }
}