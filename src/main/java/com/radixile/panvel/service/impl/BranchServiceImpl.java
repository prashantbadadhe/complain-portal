package com.radixile.panvel.service.impl;

import com.radixile.panvel.service.BranchService;
import com.radixile.panvel.domain.Branch;
import com.radixile.panvel.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Branch.
 */
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;

    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    /**
     * Save a branch.
     *
     * @param branch the entity to save
     * @return the persisted entity
     */
    @Override
    public Branch save(Branch branch) {
        log.debug("Request to save Branch : {}", branch);
        return branchRepository.save(branch);
    }

    /**
     * Get all the branches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Branch> findAll(Pageable pageable) {
        log.debug("Request to get all Branches");
        return branchRepository.findAll(pageable);
    }


    /**
     * Get one branch by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Branch> findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findById(id);
    }

    /**
     * Delete the branch by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.deleteById(id);
    }
}
