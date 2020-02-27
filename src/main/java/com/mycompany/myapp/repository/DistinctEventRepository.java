package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DistinctEvent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DistinctEvent entity.
 */
@Repository
public interface DistinctEventRepository extends JpaRepository<DistinctEvent, Long> {

    @Query(value = "select distinct distinctEvent from DistinctEvent distinctEvent left join fetch distinctEvent.sels",
        countQuery = "select count(distinct distinctEvent) from DistinctEvent distinctEvent")
    Page<DistinctEvent> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct distinctEvent from DistinctEvent distinctEvent left join fetch distinctEvent.sels")
    List<DistinctEvent> findAllWithEagerRelationships();

    @Query("select distinctEvent from DistinctEvent distinctEvent left join fetch distinctEvent.sels where distinctEvent.id =:id")
    Optional<DistinctEvent> findOneWithEagerRelationships(@Param("id") Long id);

}
