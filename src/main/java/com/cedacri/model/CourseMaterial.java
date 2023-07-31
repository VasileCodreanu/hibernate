package com.cedacri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseMaterial {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long courseMaterialId;
//  @ManyToOne//owning side becouse it has @JointColumn(FK)
//  @JoinColumn(name = "course_id", referencedColumnName = "courseID", nullable = false)

  @OneToOne(optional = false)
  @JoinColumn(name = "course_id")
  private Course course;

  private String url;


}

