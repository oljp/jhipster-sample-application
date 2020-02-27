package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Sel entity.
 */
@Repository
public interface SelRepository extends JpaRepository<Sel, Long> {

    @Query(value = "select distinct sel from Sel sel left join fetch sel.initiatingEvents left join fetch sel.associatedEvents",
        countQuery = "select count(distinct sel) from Sel sel")
    Page<Sel> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct sel from Sel sel left join fetch sel.initiatingEvents left join fetch sel.associatedEvents")
    List<Sel> findAllWithEagerRelationships();

    @Query("select sel from Sel sel left join fetch sel.initiatingEvents left join fetch sel.associatedEvents where sel.id =:id")
    Optional<Sel> findOneWithEagerRelationships(@Param("id") Long id);

}
