package com.radixile.panvel.service;

import com.radixile.panvel.domain.Branch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Branch.
 */
public interface BranchService {

    /**
     * Save a branch.
     *
     * @param branch the entity to save
     * @return the persisted entity
     */
    Branch save(Branch branch);

    /**
     * Get all the branches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Branch> findAll(Pageable pageable);


    /**
     * Get the "id" branch.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Branch> findOne(Long id);

    /**
     * Delete the "id" branch.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
