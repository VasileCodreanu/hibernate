package com.cedacri.DAO;

import static org.junit.jupiter.api.Assertions.*;

import com.cedacri.model.Teacher;
import com.cedacri.service.JpaService;
import jakarta.persistence.EntityManager;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeacherDaoTest {
  private TeacherDao teacherDao;
  private static Teacher firstTeacher;
  private static Teacher secondTeacher;

  @BeforeAll
  static void beforeAll() {
    firstTeacher = new Teacher("firstTeacher", "1teacher");
    secondTeacher = new Teacher("secondTeacher", "2teacher");
  }

  @BeforeEach
  void setUp() {
    JpaService jpaService = JpaService.getInstance();
    EntityManager em = jpaService.getTransaction();
    teacherDao = new TeacherDao(em);
  }

  @AfterEach
  void tearDown(){
    //close connection
  }
  @Test
  void getById() {
    Teacher teacherToBeSaved = new Teacher("firstTeacher", "1teacher");
    Teacher savedTeacher = teacherDao.save(teacherToBeSaved).get();
    Teacher teacherById  = teacherDao.getById(savedTeacher.getId()).get();
    assertAll(
        () -> assertNotNull(teacherById, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(teacherById.getId() > 0,
            "entity should return a valid DB id(id > 0);"),
        () -> assertEquals(teacherById.getFirstName(), teacherToBeSaved.getFirstName(),
            "Incorrect client firstName;"),
        () -> assertEquals(teacherById.getLastName(), teacherToBeSaved.getLastName(),
            "Incorrect client lastName;")
    );
  }

  @Test
  void getAll() {
    Teacher firstTeacher = new Teacher("firstTeacher", "1teacher");
    Teacher secondTeacher = new Teacher("secondTeacher", "2teacher");
    teacherDao.save(firstTeacher).get();
    teacherDao.save(secondTeacher).get();
    Set<Teacher> teachers = teacherDao.getAll();
    assertAll(
        () -> assertNotNull(teachers, "List of teachers should not be empty;"),
        () -> assertEquals(2, teachers.size(), "list size() should be equal 2"),
        () -> assertTrue(teachers.stream().findFirst().get().getId() > 0)
    );
  }

  @Test
  void save() {
    Teacher firstTeacher = new Teacher("firstTeacher", "1teacher");
    Teacher save = teacherDao.save(firstTeacher).get();
    assertAll(
        () -> assertNotNull(save, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(save.getId() > 0,
            "entity should return a valid DB id(id > 0);")
    );
  }

  @Test
  void update() {
    Teacher savedTeacher = teacherDao.save(secondTeacher).get();
    savedTeacher.setFirstName("updatedName");
    Teacher updatedTeacher = teacherDao.update(savedTeacher).get();
    assertAll(
        () -> assertNotNull(updatedTeacher, "Should return updated teacher, but entity is Null"),
        () -> assertEquals(updatedTeacher.getFirstName(), "updatedName",
            "Entities should have updated last name"),
        () -> assertEquals(updatedTeacher.getId(), savedTeacher.getId(),
            "DB should return same entity.")
    );
  }

  @Test
  void delete() {
    Teacher savedTeacher = teacherDao.save(firstTeacher).get();
    int sizeBeforeDelete = teacherDao.getAll().size();
    teacherDao.delete(savedTeacher);
    int sizeAfterDelete = teacherDao.getAll().size();
    assertAll(
        () -> assertEquals(sizeAfterDelete, sizeBeforeDelete - 1,
            "after delete Set size() should be size()-1")
    );
  }
}