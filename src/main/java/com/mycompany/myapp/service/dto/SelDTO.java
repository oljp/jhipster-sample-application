package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Sel} entity.
 */
public class SelDTO implements Serializable {

    private Long id;

    private String name;

    private String rhel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRhel() {
        return rhel;
    }

    public void setRhel(String rhel) {
        this.rhel = rhel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelDTO selDTO = (SelDTO) o;
        if (selDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rhel='" + getRhel() + "'" +
            "}";
    }
}
