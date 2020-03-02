package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Sel}.
 */
public interface SelService {

    /**
     * Save a sel.
     *
     * @param selDTO the entity to save.
     * @return the persisted entity.
     */
    SelDTO save(SelDTO selDTO);

    /**
     * Get all the sels.
     *
     * @return the list of entities.
     */
    List<SelDTO> findAll();

    /**
     * Get the "id" sel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SelDTO> findOne(Long id);

    /**
     * Delete the "id" sel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
