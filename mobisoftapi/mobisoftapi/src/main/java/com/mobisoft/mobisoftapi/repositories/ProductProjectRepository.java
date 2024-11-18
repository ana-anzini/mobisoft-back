package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.ProductProject;

@Repository
public interface ProductProjectRepository extends JpaRepository<ProductProject, Long> {

}
