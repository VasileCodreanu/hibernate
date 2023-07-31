package com.cedacri.model;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {
  private String street;
  private String houseNumber;
  private String city;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Address address = (Address) o;

    if (!Objects.equals(street, address.street)) {
      return false;
    }
    if (!Objects.equals(houseNumber, address.houseNumber)) {
      return false;
    }
    return Objects.equals(city, address.city);
  }

  @Override
  public int hashCode() {
    int result = street != null ? street.hashCode() : 0;
    result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    return result;
  }
}
