package com.cedacri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Objects;
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
  private Long id;

  @OneToOne(optional = false)//, fetch=FetchType.EAGER //, cascade=CascadeType.ALL
  @JoinColumn(name = "course_id", referencedColumnName = "id")
  private Course course;

  private String courserUrl;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CourseMaterial that = (CourseMaterial) o;

    if (!Objects.equals(id, that.id)) {
      return false;
    }
    return Objects.equals(courserUrl, that.courserUrl);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (courserUrl != null ? courserUrl.hashCode() : 0);
    return result;
  }

  public CourseMaterial(String courserUrl) {
    this.courserUrl = courserUrl;
  }

  @Override
  public String toString() {
    return "CourseMaterial{" +
        "id=" + id +
        ", course=" + course +
        ", courserUrl='" + courserUrl + '\'' +
        '}';
  }
}

