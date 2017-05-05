package com.mx.anivip.service;

import com.mx.anivip.domain.Pass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Pass.
 */
public interface PassService {

    /**
     * Save a pass.
     *
     * @param pass the entity to save
     * @return the persisted entity
     */
    Pass save(Pass pass);

    /**
     *  Get all the passes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Pass> findAll(Pageable pageable);

    /**
     *  Get the "id" pass.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Pass findOne(String id);

    /**
     *  Delete the "id" pass.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
