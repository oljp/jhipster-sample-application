package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DistinctEventService;
import com.mycompany.myapp.domain.DistinctEvent;
import com.mycompany.myapp.repository.DistinctEventRepository;
import com.mycompany.myapp.service.dto.DistinctEventDTO;
import com.mycompany.myapp.service.mapper.DistinctEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DistinctEvent}.
 */
@Service
@Transactional
public class DistinctEventServiceImpl implements DistinctEventService {

    private final Logger log = LoggerFactory.getLogger(DistinctEventServiceImpl.class);

    private final DistinctEventRepository distinctEventRepository;

    private final DistinctEventMapper distinctEventMapper;

    public DistinctEventServiceImpl(DistinctEventRepository distinctEventRepository, DistinctEventMapper distinctEventMapper) {
        this.distinctEventRepository = distinctEventRepository;
        this.distinctEventMapper = distinctEventMapper;
    }

    /**
     * Save a distinctEvent.
     *
     * @param distinctEventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DistinctEventDTO save(DistinctEventDTO distinctEventDTO) {
        log.debug("Request to save DistinctEvent : {}", distinctEventDTO);
        DistinctEvent distinctEvent = distinctEventMapper.toEntity(distinctEventDTO);
        distinctEvent = distinctEventRepository.save(distinctEvent);
        return distinctEventMapper.toDto(distinctEvent);
    }

    /**
     * Get all the distinctEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DistinctEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DistinctEvents");
        return distinctEventRepository.findAll(pageable)
            .map(distinctEventMapper::toDto);
    }

    /**
     * Get all the distinctEvents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DistinctEventDTO> findAllWithEagerRelationships(Pageable pageable) {
        return distinctEventRepository.findAllWithEagerRelationships(pageable).map(distinctEventMapper::toDto);
    }

    /**
     * Get one distinctEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DistinctEventDTO> findOne(Long id) {
        log.debug("Request to get DistinctEvent : {}", id);
        return distinctEventRepository.findOneWithEagerRelationships(id)
            .map(distinctEventMapper::toDto);
    }

    /**
     * Delete the distinctEvent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DistinctEvent : {}", id);
        distinctEventRepository.deleteById(id);
    }
}
