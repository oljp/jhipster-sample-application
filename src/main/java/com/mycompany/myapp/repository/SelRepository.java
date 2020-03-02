package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelRepository extends JpaRepository<Sel, Long> {

}
