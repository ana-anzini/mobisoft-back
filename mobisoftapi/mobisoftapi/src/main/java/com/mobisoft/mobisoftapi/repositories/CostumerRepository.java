package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Costumer;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {

}
