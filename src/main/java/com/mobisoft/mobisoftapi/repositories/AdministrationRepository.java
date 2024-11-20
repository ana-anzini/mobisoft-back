package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Administration;

@Repository
public interface AdministrationRepository extends JpaRepository<Administration, Long> {

}
