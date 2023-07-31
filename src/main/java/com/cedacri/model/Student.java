package com.cedacri.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentId;
  private String firstName;
  private String lastName;
  private String dob;
  private Gender gender;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE } )//..
  @JoinTable(
      name = "join_table_course_student",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> coursesList =  new HashSet<>();

  @Embedded
  @AttributeOverrides({
      @AttributeOverride( name = "street", column = @Column(name = "home_street"))  })
  private Address address;

  public Student(String firstName, String lastName, String dob, Gender gender, Address address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = dob;
    this.gender = gender;
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Student student = (Student) o;
    return getStudentId() != null && Objects.equals(getStudentId(), student.getStudentId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
