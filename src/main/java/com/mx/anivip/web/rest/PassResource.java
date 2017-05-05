package com.mx.anivip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.anivip.domain.Pass;
import com.mx.anivip.service.PassService;
import com.mx.anivip.web.rest.util.HeaderUtil;
import com.mx.anivip.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing Pass.
 */
@RestController
@RequestMapping("/api")
public class PassResource {

    private final Logger log = LoggerFactory.getLogger(PassResource.class);

    private static final String ENTITY_NAME = "pass";
        
    private final PassService passService;

    public PassResource(PassService passService) {
        this.passService = passService;
    }

    /**
     * POST  /passes : Create a new pass.
     *
     * @param pass the pass to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pass, or with status 400 (Bad Request) if the pass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/passes")
    @Timed
    public ResponseEntity<Pass> createPass(@RequestBody Pass pass) throws URISyntaxException {
        log.debug("REST request to save Pass : {}", pass);
        if (pass.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pass cannot already have an ID")).body(null);
        }
        Pass result = passService.save(pass);
        return ResponseEntity.created(new URI("/api/passes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passes : Updates an existing pass.
     *
     * @param pass the pass to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pass,
     * or with status 400 (Bad Request) if the pass is not valid,
     * or with status 500 (Internal Server Error) if the pass couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/passes")
    @Timed
    public ResponseEntity<Pass> updatePass(@RequestBody Pass pass) throws URISyntaxException {
        log.debug("REST request to update Pass : {}", pass);
        if (pass.getId() == null) {
            return createPass(pass);
        }
        Pass result = passService.save(pass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pass.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passes : get all the passes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of passes in body
     */
    @GetMapping("/passes")
    @Timed
    public ResponseEntity<List<Pass>> getAllPasses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Passes");
        Page<Pass> page = passService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/passes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /passes/:id : get the "id" pass.
     *
     * @param id the id of the pass to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pass, or with status 404 (Not Found)
     */
    @GetMapping("/passes/{id}")
    @Timed
    public ResponseEntity<Pass> getPass(@PathVariable String id) {
        log.debug("REST request to get Pass : {}", id);
        Pass pass = passService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pass));
    }

    /**
     * DELETE  /passes/:id : delete the "id" pass.
     *
     * @param id the id of the pass to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/passes/{id}")
    @Timed
    public ResponseEntity<Void> deletePass(@PathVariable String id) {
        log.debug("REST request to delete Pass : {}", id);
        passService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
