package com.cedacri.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
  private Long courseId;
  private String title;

  @ManyToMany(mappedBy = "coursesList")
  private Set<Student> studentList=  new HashSet<>();

  // unidirectional from many to one
  @ManyToOne()//cascade = {CascadeType.ALL}
  @JoinColumn(name = "teacher_id")//aici se face FK pentru Teacher(care este numele la Fk in acest tabel)
  //Why? By saying "cascade ALL" on the child entity Transaction you require that every DB operation gets propagated to the parent entity Account. If you then do persist(transaction), persist(account) will be invoked as well.
  private Teacher teacher;

//  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
  private CourseMaterial courseMaterialList;

  public Course(String title) {
    this.title = title;
  }


  public void addStudent(Student student) {
    this.studentList.add(student);
    student.getCoursesList().add(this);
  }

  public void removeStudent(Student student) {
    studentList.remove(student);
    student.setCoursesList(null);
  }

  public void addTeacher(Teacher teacher){
    if (teacher != null) {
      teacher.getCourses().add(this);
    } else if (this.teacher != null) {
      this.teacher.getCourses().remove(this);
    }
    this.teacher = teacher;
  }

//  public void addStudent(Student student) {
//    this.studentList.add(student);
//  }

  @Override
  public String toString() {
    return "Course{" +
        "courseId=" + courseId +
        ", title='" + title + '\'' +
        ", studentList=" + studentList +
        ", teacher=" + teacher +
        ", courseMaterialList=" + courseMaterialList +
        '}';
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

    if (!Objects.equals(courseId, course.courseId)) {
      return false;
    }
    if (!Objects.equals(title, course.title)) {
      return false;
    }
    if (!Objects.equals(studentList, course.studentList)) {
      return false;
    }
    if (!Objects.equals(teacher, course.teacher)) {
      return false;
    }
    return Objects.equals(courseMaterialList, course.courseMaterialList);
  }

  @Override
  public int hashCode() {
    int result = courseId != null ? courseId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (studentList != null ? studentList.hashCode() : 0);
    result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
    result = 31 * result + (courseMaterialList != null ? courseMaterialList.hashCode() : 0);
    return result;
  }

//mappedBy property is what we use to tell Hibernate which variable we are using to represent the parent class in our child class.
  // mappedBy flag in the @OneToMany annotation on the referencing side.

  //We keep our @ManyToOne mapping on the Course entity.  the use of the mappedBy flag in the @OneToMany annotation on the referencing side. Without it, we wouldn't have a two-way relationship. We'd have two one-way relationships. Both entities would be mapping foreign keys for the other entity.
  //With it, we're telling JPA that the field is already mapped by another entity. It's mapped by the teacher field of the Course entity.

  //JPA specification under section 2.9, it's a good practice to mark the many-to-one side as the owning side.
  //Item would be the owning side and Cart the inverse side
  //By including the mappedBy attribute in the Cart class, we mark it as the inverse side. At the same time, we also annotate the Item.cart field with @ManyToOne, making Item the owning side.

  //The @JoinColumn annotation defines the actual physical mapping on the owning side. On the other hand, the referencing side is defined using the mappedBy attribute of the @OneToMany annotation.

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
