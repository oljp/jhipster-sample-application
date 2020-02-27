package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DistinctEvent} entity.
 */
public class DistinctEventDTO implements Serializable {

    private Long id;

    private String provider;

    private String dataset;


    private Set<SelDTO> sels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public Set<SelDTO> getSels() {
        return sels;
    }

    public void setSels(Set<SelDTO> sels) {
        this.sels = sels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DistinctEventDTO distinctEventDTO = (DistinctEventDTO) o;
        if (distinctEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), distinctEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DistinctEventDTO{" +
            "id=" + getId() +
            ", provider='" + getProvider() + "'" +
            ", dataset='" + getDataset() + "'" +
            "}";
    }
}
