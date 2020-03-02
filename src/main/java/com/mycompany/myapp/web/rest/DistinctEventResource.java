package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DistinctEventService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.DistinctEventDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DistinctEvent}.
 */
@RestController
@RequestMapping("/api")
public class DistinctEventResource {

    private final Logger log = LoggerFactory.getLogger(DistinctEventResource.class);

    private static final String ENTITY_NAME = "distinctEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistinctEventService distinctEventService;

    public DistinctEventResource(DistinctEventService distinctEventService) {
        this.distinctEventService = distinctEventService;
    }

    /**
     * {@code POST  /distinct-events} : Create a new distinctEvent.
     *
     * @param distinctEventDTO the distinctEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distinctEventDTO, or with status {@code 400 (Bad Request)} if the distinctEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/distinct-events")
    public ResponseEntity<DistinctEventDTO> createDistinctEvent(@RequestBody DistinctEventDTO distinctEventDTO) throws URISyntaxException {
        log.debug("REST request to save DistinctEvent : {}", distinctEventDTO);
        if (distinctEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new distinctEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistinctEventDTO result = distinctEventService.save(distinctEventDTO);
        return ResponseEntity.created(new URI("/api/distinct-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distinct-events} : Updates an existing distinctEvent.
     *
     * @param distinctEventDTO the distinctEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distinctEventDTO,
     * or with status {@code 400 (Bad Request)} if the distinctEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distinctEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/distinct-events")
    public ResponseEntity<DistinctEventDTO> updateDistinctEvent(@RequestBody DistinctEventDTO distinctEventDTO) throws URISyntaxException {
        log.debug("REST request to update DistinctEvent : {}", distinctEventDTO);
        if (distinctEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DistinctEventDTO result = distinctEventService.save(distinctEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, distinctEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /distinct-events} : get all the distinctEvents.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distinctEvents in body.
     */
    @GetMapping("/distinct-events")
    public ResponseEntity<List<DistinctEventDTO>> getAllDistinctEvents(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DistinctEvents");
        Page<DistinctEventDTO> page;
        if (eagerload) {
            page = distinctEventService.findAllWithEagerRelationships(pageable);
        } else {
            page = distinctEventService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /distinct-events/:id} : get the "id" distinctEvent.
     *
     * @param id the id of the distinctEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distinctEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/distinct-events/{id}")
    public ResponseEntity<DistinctEventDTO> getDistinctEvent(@PathVariable Long id) {
        log.debug("REST request to get DistinctEvent : {}", id);
        Optional<DistinctEventDTO> distinctEventDTO = distinctEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distinctEventDTO);
    }

    /**
     * {@code DELETE  /distinct-events/:id} : delete the "id" distinctEvent.
     *
     * @param id the id of the distinctEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/distinct-events/{id}")
    public ResponseEntity<Void> deleteDistinctEvent(@PathVariable Long id) {
        log.debug("REST request to delete DistinctEvent : {}", id);
        distinctEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
