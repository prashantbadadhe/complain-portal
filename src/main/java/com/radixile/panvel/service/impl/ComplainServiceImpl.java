package com.radixile.panvel.service.impl;

import com.radixile.panvel.service.ComplainService;
import com.radixile.panvel.domain.Complain;
import com.radixile.panvel.repository.ComplainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Complain.
 */
@Service
@Transactional
public class ComplainServiceImpl implements ComplainService {

    private final Logger log = LoggerFactory.getLogger(ComplainServiceImpl.class);

    private final ComplainRepository complainRepository;

    public ComplainServiceImpl(ComplainRepository complainRepository) {
        this.complainRepository = complainRepository;
    }

    /**
     * Save a complain.
     *
     * @param complain the entity to save
     * @return the persisted entity
     */
    @Override
    public Complain save(Complain complain) {
        log.debug("Request to save Complain : {}", complain);
        return complainRepository.save(complain);
    }

    /**
     * Get all the complains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Complain> findAll(Pageable pageable) {
        log.debug("Request to get all Complains");
        return complainRepository.findAll(pageable);
    }


    /**
     * Get one complain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Complain> findOne(Long id) {
        log.debug("Request to get Complain : {}", id);
        return complainRepository.findById(id);
    }

    /**
     * Delete the complain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Complain : {}", id);
        complainRepository.deleteById(id);
    }
}
