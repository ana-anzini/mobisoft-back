package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Deliveries;

@Repository
public interface DeliveriesRepository extends JpaRepository<Deliveries, Long>{

}
