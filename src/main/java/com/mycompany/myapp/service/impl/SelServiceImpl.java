package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SelService;
import com.mycompany.myapp.domain.Sel;
import com.mycompany.myapp.repository.SelRepository;
import com.mycompany.myapp.service.dto.SelDTO;
import com.mycompany.myapp.service.mapper.SelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sel}.
 */
@Service
@Transactional
public class SelServiceImpl implements SelService {

    private final Logger log = LoggerFactory.getLogger(SelServiceImpl.class);

    private final SelRepository selRepository;

    private final SelMapper selMapper;

    public SelServiceImpl(SelRepository selRepository, SelMapper selMapper) {
        this.selRepository = selRepository;
        this.selMapper = selMapper;
    }

    /**
     * Save a sel.
     *
     * @param selDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SelDTO save(SelDTO selDTO) {
        log.debug("Request to save Sel : {}", selDTO);
        Sel sel = selMapper.toEntity(selDTO);
        sel = selRepository.save(sel);
        return selMapper.toDto(sel);
    }

    /**
     * Get all the sels.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SelDTO> findAll() {
        log.debug("Request to get all Sels");
        return selRepository.findAll().stream()
            .map(selMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SelDTO> findOne(Long id) {
        log.debug("Request to get Sel : {}", id);
        return selRepository.findById(id)
            .map(selMapper::toDto);
    }

    /**
     * Delete the sel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sel : {}", id);
        selRepository.deleteById(id);
    }
}
