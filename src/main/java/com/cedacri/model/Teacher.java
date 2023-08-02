package com.cedacri.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;

  @ManyToMany(mappedBy = "teachers")
  private Set<Course> courses=new HashSet<>();

  public Teacher(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void addCourse(Course course){
    courses.add(course);
//    course.setTeacher(this);
  }
  public void removeCourse(Course course) {
    courses.remove(course);
//    course.setTeacher(null);
  }
}



//A bidirectional mapping always requires a mappedBy side.
// lower-level details are now mapped by “the property on the other side,” named teacher.
//When we make the User persistent, Hibernate makes the shippingAddress persistent and generates the identifier for the primary key automatically.

//Note: It's a good practice to put the owning side of a relationship
// in the cl