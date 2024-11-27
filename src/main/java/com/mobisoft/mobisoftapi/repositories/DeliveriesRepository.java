package com.mobisoft.mobisoftapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Deliveries;

@Repository
public interface DeliveriesRepository extends JpaRepository<Deliveries, Long>{

	Deliveries findByProjectId(Long projectId);
	
	List<Deliveries> findByUserGroupId(Long userGroupId);
}
