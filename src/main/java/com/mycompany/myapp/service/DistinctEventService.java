package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DistinctEventDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DistinctEvent}.
 */
public interface DistinctEventService {

    /**
     * Save a distinctEvent.
     *
     * @param distinctEventDTO the entity to save.
     * @return the persisted entity.
     */
    DistinctEventDTO save(DistinctEventDTO distinctEventDTO);

    /**
     * Get all the distinctEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistinctEventDTO> findAll(Pageable pageable);

    /**
     * Get all the distinctEvents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DistinctEventDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" distinctEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistinctEventDTO> findOne(Long id);

    /**
     * Delete the "id" distinctEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
