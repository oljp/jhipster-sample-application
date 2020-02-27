package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sel.
 */
@Entity
@Table(name = "sel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rhel")
    private String rhel;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sel_initiating_event",
               joinColumns = @JoinColumn(name = "sel_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "initiating_event_id", referencedColumnName = "id"))
    private Set<DistinctEvent> initiatingEvents = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sel_associated_event",
               joinColumns = @JoinColumn(name = "sel_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "associated_event_id", referencedColumnName = "id"))
    private Set<DistinctEvent> associatedEvents = new HashSet<>();

    @ManyToMany(mappedBy = "sels")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<DistinctEvent> distinctEvents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRhel() {
        return rhel;
    }

    public Sel rhel(String rhel) {
        this.rhel = rhel;
        return this;
    }

    public void setRhel(String rhel) {
        this.rhel = rhel;
    }

    public Set<DistinctEvent> getInitiatingEvents() {
        return initiatingEvents;
    }

    public Sel initiatingEvents(Set<DistinctEvent> distinctEvents) {
        this.initiatingEvents = distinctEvents;
        return this;
    }

    public Sel addInitiatingEvent(DistinctEvent distinctEvent) {
        this.initiatingEvents.add(distinctEvent);
        distinctEvent.getInitiatingSels().add(this);
        return this;
    }

    public Sel removeInitiatingEvent(DistinctEvent distinctEvent) {
        this.initiatingEvents.remove(distinctEvent);
        distinctEvent.getInitiatingSels().remove(this);
        return this;
    }

    public void setInitiatingEvents(Set<DistinctEvent> distinctEvents) {
        this.initiatingEvents = distinctEvents;
    }

    public Set<DistinctEvent> getAssociatedEvents() {
        return associatedEvents;
    }

    public Sel associatedEvents(Set<DistinctEvent> distinctEvents) {
        this.associatedEvents = distinctEvents;
        return this;
    }

    public Sel addAssociatedEvent(DistinctEvent distinctEvent) {
        this.associatedEvents.add(distinctEvent);
        distinctEvent.getAssociatedSels().add(this);
        return this;
    }

    public Sel removeAssociatedEvent(DistinctEvent distinctEvent) {
        this.associatedEvents.remove(distinctEvent);
        distinctEvent.getAssociatedSels().remove(this);
        return this;
    }

    public void setAssociatedEvents(Set<DistinctEvent> distinctEvents) {
        this.associatedEvents = distinctEvents;
    }

    public Set<DistinctEvent> getDistinctEvents() {
        return distinctEvents;
    }

    public Sel distinctEvents(Set<DistinctEvent> distinctEvents) {
        this.distinctEvents = distinctEvents;
        return this;
    }

    public Sel addDistinctEvent(DistinctEvent distinctEvent) {
        this.distinctEvents.add(distinctEvent);
        distinctEvent.getSels().add(this);
        return this;
    }

    public Sel removeDistinctEvent(DistinctEvent distinctEvent) {
        this.distinctEvents.remove(distinctEvent);
        distinctEvent.getSels().remove(this);
        return this;
    }

    public void setDistinctEvents(Set<DistinctEvent> distinctEvents) {
        this.distinctEvents = distinctEvents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sel)) {
            return false;
        }
        return id != null && id.equals(((Sel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rhel='" + getRhel() + "'" +
            "}";
    }
}
