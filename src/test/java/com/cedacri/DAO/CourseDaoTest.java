package com.cedacri.DAO;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cedacri.model.Address;
import com.cedacri.model.Course;
import com.cedacri.model.CourseMaterial;
import com.cedacri.model.Gender;
import com.cedacri.model.Student;
import com.cedacri.service.JpaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseDaoTest {
  private Dao<Course> courseDao;
  private static Course javaCourse;
  private static Course hibernateCourse;
  private CourseMaterial javaCourseMaterial;
  EntityManager em;
  StudentDao studentDao;

  @BeforeEach
  void setUp() {
    em = JpaService.getInstance().getTransaction();
    courseDao  = new CourseDao(em);
    studentDao = new StudentDao(em);
    javaCourse =  new Course("Java");
    hibernateCourse = new Course("Hibernate");
    javaCourseMaterial = new CourseMaterial("site: yahoo.com");
  }

  @Test
  void getById() {
    Course savedJavaCourse = courseDao.save(javaCourse).get();
    Course courseById = courseDao.getById(savedJavaCourse.getId()).get();

    assertAll(
        () -> assertNotNull(courseById, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(courseById.getId() > 0,
            "entity should return a valid DB id(id > 0);"),
        () -> assertEquals(courseById.getTitle(), savedJavaCourse.getTitle(),
            "Incorrect Course title;"),
        () -> assertEquals(savedJavaCourse.getId(), courseById.getId(),
            "IDs should be same;")
    );
  }

  @Test
  void getAll() {
    courseDao.save(javaCourse).get();
    courseDao.save(hibernateCourse).get();

    Set<Course> courses = courseDao.getAll();
    assertAll(
        () -> assertNotNull(courses, "List of teachers should not be empty;"),
        () -> assertEquals(2, courses.size(), "list size() should be equal 2"),
        () -> assertTrue(courses.stream().findFirst().get().getId() > 0),
        () -> assertTrue(courses.stream().reduce((first, second) -> second).get().getId() > 0)
    );
  }

  @Test
  void save() {
    Course savedJavaCourse = courseDao.save(javaCourse).get();

    assertAll(
        () -> assertNotNull(savedJavaCourse, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(savedJavaCourse.getId() > 0,
            "entity should return a valid DB id(id > 0);")
    );
  }

  @Test
  void update() {
    Course savedJavaCourse = courseDao.save(javaCourse).get();
    savedJavaCourse.setTitle("UpdateJavaTitle");
    Course updatedJavaCourse = courseDao.update(savedJavaCourse).get();

    assertAll(
        () -> assertNotNull(updatedJavaCourse, "Should return updated Course title, but entity is Null"),
        () -> assertEquals(updatedJavaCourse.getTitle(), "UpdateJavaTitle",
            "Entities should have updated title;"),
        () -> assertEquals(updatedJavaCourse.getId(), savedJavaCourse.getId(),
            "DB should return same ID for same entity.")
    );
  }

  @Test
  void delete() {
    Course savedJavaCourse = courseDao.save(javaCourse).get();
    int sizeBeforeDelete = courseDao.getAll().size();
    courseDao.delete(savedJavaCourse);
    int sizeAfterDelete = courseDao.getAll().size();

    assertAll(
        () -> assertEquals(sizeAfterDelete, sizeBeforeDelete - 1,
            "after delete(), initial Set.size() should be greater by one, than Set.size() after delete();")
    );
  }

  @Test
  void addNewStudentToCourseFoundByName() {
    Student michael = new Student("michael", "smith", LocalDate.now().toString()
        , Gender.MALE, new Address("michaelstreet", "michaelhouseNr", "michaelchisinau"));

    TypedQuery<Course> query = em.createQuery(
            "SELECT c FROM Course c where c.title=?1", Course.class)
        .setParameter(1, "java");
    Course javaCourse = query.getSingleResult();

    Set<Student> studentsBeforeSave = studentDao.getAll();

    javaCourse.addStudent(michael);
    courseDao.save(javaCourse);

    Set<Student> studentsAfterSave = studentDao.getAll();
    assertEquals(studentsBeforeSave.size(), studentsAfterSave.size() - 1);
  }
}
