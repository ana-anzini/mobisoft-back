package com.mobisoft.mobisoftapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Costumer;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {

	List<Costumer> findByUserGroupId(Long userGroupId);
}
