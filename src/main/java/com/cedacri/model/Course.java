package com.cedacri.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "join_table_course_student",
      joinColumns = @JoinColumn(name = "fk_course", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "fk_student", referencedColumnName = "id"))
  private Set<Student> students;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "join_table_course_teacher",
      joinColumns = @JoinColumn(name = "fk_course", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "fk_teacher", referencedColumnName = "id"))
  private Set<Teacher> teachers;

  @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
  private CourseMaterial courseMaterial;

  public Course(String title) {
    this.title = title;
  }

  public void addCourseMaterial(CourseMaterial courseMaterial){
    this.courseMaterial = courseMaterial;
    courseMaterial.setCourse(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Course course = (Course) o;

    if (!Objects.equals(id, course.id)) {
      return false;
    }
    return Objects.equals(title, course.title);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    return result;
  }

  public void removeCourseMaterial(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.getCourses().remove(this);
    }
  public void addTeacher(Teacher teacher){
    if(this.teachers == null){
      teachers =  new HashSet<>();
    }
    this.teachers.add(teacher);
    teacher.getCourses().add(this);
  }
  public void removeTeacher(Teacher teacher) {
    this.teachers.remove(teacher);
    teacher.getCourses().remove(this);
  }
  public void addStudent(Student student){
    if(this.students == null){
      this.students =  new HashSet<>();
    }
    this.students.add(student);
    student.getCourses().add(this);
  }
  public void removeStudent(Student student) {
    this.students.remove(student);
    student.getCourses().remove(this);
  }

  //JPA specification under section 2.9, it's a good practice to mark the many-to-one side as the owning side.
  //tem would be the owning side and Cart the inverse side
  //By including the mappedBy attribute in the Cart class, we mark it as the inverse side.
  //At the same time, we also annotate the Item.cart field with @ManyToOne, making Item the owning side.

  //    session.persist(person);
  //    session.flush();
  //    session.clear();

  //    //jpa -- PERSIST MERGE REMOVE REFRESH DETACH
  //    // Hibernate supports three additional Cascade Types --REPLICATE SAVE_UPDATE LOCK
  //    //CascadeType.ALL propagates all operations — including Hibernate-specific ones — from a parent to a child entity.

  //CascadeType.ALL propagates all operations — including Hibernate-specific ones —
  // from a parent to a child entity.
  //CascadeType.MERGE -- copies the state of the given object onto the persistent
  // object with the same identifier. CascadeType.MERGE propagates the merge operation
  // from a parent to a child entity.

  //CascadeType.DETACH
  //The detach operation removes the entity from the persistent context. When we use CascadeType.DETACH,
  //the child entity will also get removed from the persistent context.

  //CascadeType.LOCK reattaches the entity and its associated child entity with the persistent context again.

  //CascadeType.REFRESH - Refresh operations reread the value of a given instance from the database.
  // In some cases, we may change an instance after persisting in the database, but later we need to undo those changes.
  //In that kind of scenario, this may be useful. When we use this operation with Cascade Type REFRESH,
  // the child entity also gets reloaded from the database whenever the parent entity is refreshed.

  //CascadeType.REPLICATE
  //The replicate operation is used when we have more than one data source and we want the data in sync.
  // With CascadeType.REPLICATE, a sync operation also propagates to child entities whenever performed on the parent entity.
}
