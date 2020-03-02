package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DistinctEvent.
 */
@Entity
@Table(name = "distinct_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DistinctEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "dataset")
    private String dataset;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "distinct_event_initiating_sel",
               joinColumns = @JoinColumn(name = "distinct_event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "initiating_sel_id", referencedColumnName = "id"))
    private Set<Sel> initiatingSels = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "distinct_event_associated_sel",
               joinColumns = @JoinColumn(name = "distinct_event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "associated_sel_id", referencedColumnName = "id"))
    private Set<Sel> associatedSels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public DistinctEvent provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDataset() {
        return dataset;
    }

    public DistinctEvent dataset(String dataset) {
        this.dataset = dataset;
        return this;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public Set<Sel> getInitiatingSels() {
        return initiatingSels;
    }

    public DistinctEvent initiatingSels(Set<Sel> sels) {
        this.initiatingSels = sels;
        return this;
    }

    public DistinctEvent addInitiatingSel(Sel sel) {
        this.initiatingSels.add(sel);
        sel.getInitiatingEvents().add(this);
        return this;
    }

    public DistinctEvent removeInitiatingSel(Sel sel) {
        this.initiatingSels.remove(sel);
        sel.getInitiatingEvents().remove(this);
        return this;
    }

    public void setInitiatingSels(Set<Sel> sels) {
        this.initiatingSels = sels;
    }

    public Set<Sel> getAssociatedSels() {
        return associatedSels;
    }

    public DistinctEvent associatedSels(Set<Sel> sels) {
        this.associatedSels = sels;
        return this;
    }

    public DistinctEvent addAssociatedSel(Sel sel) {
        this.associatedSels.add(sel);
        sel.getAssociatedEvents().add(this);
        return this;
    }

    public DistinctEvent removeAssociatedSel(Sel sel) {
        this.associatedSels.remove(sel);
        sel.getAssociatedEvents().remove(this);
        return this;
    }

    public void setAssociatedSels(Set<Sel> sels) {
        this.associatedSels = sels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistinctEvent)) {
            return false;
        }
        return id != null && id.equals(((DistinctEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DistinctEvent{" +
            "id=" + getId() +
            ", provider='" + getProvider() + "'" +
            ", dataset='" + getDataset() + "'" +
            "}";
    }
}
