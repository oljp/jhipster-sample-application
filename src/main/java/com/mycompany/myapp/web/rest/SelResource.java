package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.SelService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.SelDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Sel}.
 */
@RestController
@RequestMapping("/api")
public class SelResource {

    private final Logger log = LoggerFactory.getLogger(SelResource.class);

    private static final String ENTITY_NAME = "sel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SelService selService;

    public SelResource(SelService selService) {
        this.selService = selService;
    }

    /**
     * {@code POST  /sels} : Create a new sel.
     *
     * @param selDTO the selDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new selDTO, or with status {@code 400 (Bad Request)} if the sel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sels")
    public ResponseEntity<SelDTO> createSel(@RequestBody SelDTO selDTO) throws URISyntaxException {
        log.debug("REST request to save Sel : {}", selDTO);
        if (selDTO.getId() != null) {
            throw new BadRequestAlertException("A new sel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelDTO result = selService.save(selDTO);
        return ResponseEntity.created(new URI("/api/sels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sels} : Updates an existing sel.
     *
     * @param selDTO the selDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selDTO,
     * or with status {@code 400 (Bad Request)} if the selDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the selDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sels")
    public ResponseEntity<SelDTO> updateSel(@RequestBody SelDTO selDTO) throws URISyntaxException {
        log.debug("REST request to update Sel : {}", selDTO);
        if (selDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SelDTO result = selService.save(selDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, selDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sels} : get all the sels.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sels in body.
     */
    @GetMapping("/sels")
    public List<SelDTO> getAllSels(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Sels");
        return selService.findAll();
    }

    /**
     * {@code GET  /sels/:id} : get the "id" sel.
     *
     * @param id the id of the selDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the selDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sels/{id}")
    public ResponseEntity<SelDTO> getSel(@PathVariable Long id) {
        log.debug("REST request to get Sel : {}", id);
        Optional<SelDTO> selDTO = selService.findOne(id);
        return ResponseUtil.wrapOrNotFound(selDTO);
    }

    /**
     * {@code DELETE  /sels/:id} : delete the "id" sel.
     *
     * @param id the id of the selDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sels/{id}")
    public ResponseEntity<Void> deleteSel(@PathVariable Long id) {
        log.debug("REST request to delete Sel : {}", id);
        selService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
