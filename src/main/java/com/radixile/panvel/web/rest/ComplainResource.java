package com.radixile.panvel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.radixile.panvel.domain.Complain;
import com.radixile.panvel.service.ComplainService;
import com.radixile.panvel.web.rest.errors.BadRequestAlertException;
import com.radixile.panvel.web.rest.util.HeaderUtil;
import com.radixile.panvel.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Complain.
 */
@RestController
@RequestMapping("/api")
public class ComplainResource {

    private final Logger log = LoggerFactory.getLogger(ComplainResource.class);

    private static final String ENTITY_NAME = "complain";

    private final ComplainService complainService;

    public ComplainResource(ComplainService complainService) {
        this.complainService = complainService;
    }

    /**
     * POST  /complains : Create a new complain.
     *
     * @param complain the complain to create
     * @return the ResponseEntity with status 201 (Created) and with body the new complain, or with status 400 (Bad Request) if the complain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/complains")
    @Timed
    public ResponseEntity<Complain> createComplain(@RequestBody Complain complain) throws URISyntaxException {
        log.debug("REST request to save Complain : {}", complain);
        if (complain.getId() != null) {
            throw new BadRequestAlertException("A new complain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Complain result = complainService.save(complain);
        return ResponseEntity.created(new URI("/api/complains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /complains : Updates an existing complain.
     *
     * @param complain the complain to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated complain,
     * or with status 400 (Bad Request) if the complain is not valid,
     * or with status 500 (Internal Server Error) if the complain couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/complains")
    @Timed
    public ResponseEntity<Complain> updateComplain(@RequestBody Complain complain) throws URISyntaxException {
        log.debug("REST request to update Complain : {}", complain);
        if (complain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Complain result = complainService.save(complain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, complain.getId().toString()))
            .body(result);
    }

    /**
     * GET  /complains : get all the complains.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of complains in body
     */
    @GetMapping("/complains")
    @Timed
    public ResponseEntity<List<Complain>> getAllComplains(Pageable pageable) {
        log.debug("REST request to get a page of Complains");
        Page<Complain> page = complainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/complains");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /complains/:id : get the "id" complain.
     *
     * @param id the id of the complain to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the complain, or with status 404 (Not Found)
     */
    @GetMapping("/complains/{id}")
    @Timed
    public ResponseEntity<Complain> getComplain(@PathVariable Long id) {
        log.debug("REST request to get Complain : {}", id);
        Optional<Complain> complain = complainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complain);
    }

    /**
     * DELETE  /complains/:id : delete the "id" complain.
     *
     * @param id the id of the complain to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/complains/{id}")
    @Timed
    public ResponseEntity<Void> deleteComplain(@PathVariable Long id) {
        log.debug("REST request to delete Complain : {}", id);
        complainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
