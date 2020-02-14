package com.radixile.panvel.repository;

import com.radixile.panvel.domain.Complain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Complain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {

}
