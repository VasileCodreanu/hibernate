package com.cedacri.DAO;

import static org.junit.jupiter.api.Assertions.*;

import com.cedacri.model.Address;
import com.cedacri.model.Gender;
import com.cedacri.model.Student;
import com.cedacri.service.JpaService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentDaoTest {

  private StudentDao studentDao;
  private static Student firstStudent;
  private static Student secondStudent;

  @BeforeAll
  static void beforeAll() {

  }

  @BeforeEach
  void setUp() {
    JpaService jpaService = JpaService.getInstance();
    EntityManager em = jpaService.getTransaction();
    studentDao = new StudentDao(em);

    firstStudent  = new Student("firstStudent", "smith", LocalDate.now().toString()
        , Gender.MALE, new Address("1street", "1houseNr", "1chisinau"));

    secondStudent  = new Student("secondStudent", "chan", LocalDate.now().toString()
        , Gender.FEMALE, new Address("2street", "2houseNr", "2chisinau"));
  }

  @AfterEach
  void tearDown(){
    firstStudent.setId(null);
    secondStudent.setId(null);
  }

  @Test
  void getById() {
    Student savedStudent = studentDao.save(firstStudent).get();
    Student studentById = studentDao.getById(savedStudent.getId()).get();

    assertAll(
        () -> assertNotNull(studentById, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(studentById.getId() > 0,
            "entity should return a valid DB id(id > 0);"),
        () -> assertEquals(studentById.getFirstName(), firstStudent.getFirstName(),
            "Incorrect client firstName;"),
        () -> assertEquals(studentById.getLastName(), firstStudent.getLastName(),
            "Incorrect client lastName;")
    );
  }

  @Test
  void getAll() {
    studentDao.save(firstStudent).get();
    studentDao.save(secondStudent).get();

    Set<Student> students = studentDao.getAll();
    assertAll(
        () -> assertNotNull(students, "List of teachers should not be empty;"),
        () -> assertEquals(2, students.size(), "list size() should be equal 2"),
        () -> assertTrue(students.stream().findFirst().get().getId() > 0)
    );
  }

  @Test
  void save() {
    Student savedStudent = studentDao.save(firstStudent).get();

    assertAll(
        () -> assertNotNull(savedStudent, "should return a valid entity(is returning NULL);"),
        () -> assertTrue(savedStudent.getId() > 0,
            "entity should return a valid DB id(id > 0);")
    );
  }

  @Test
  void update() {
    Student savedStudent= studentDao.save(firstStudent).get();
    savedStudent.setFirstName("updatedName");
    Student updatedStudent = studentDao.update(savedStudent).get();

    assertAll(
        () -> assertNotNull(updatedStudent, "Should return updated Student, but entity is Null"),
        () -> assertEquals(updatedStudent.getFirstName(), "updatedName",
            "Entities should have updated lastName"),
        () -> assertEquals(updatedStudent.getId(), updatedStudent.getId(),
            "DB should return same entity.")
    );
  }

  @Test
  void delete() {
    Student savedStudent = studentDao.save(firstStudent).get();
    int sizeBeforeDelete = studentDao.getAll().size();
    studentDao.delete(savedStudent);
    int sizeAfterDelete = studentDao.getAll().size();
    assertAll(
        () -> assertEquals(sizeAfterDelete, sizeBeforeDelete - 1,
            "after delete Set size() should be size()-1")
    );
  }

}