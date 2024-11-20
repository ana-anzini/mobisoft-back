package com.mobisoft.mobisoftapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.ProductProject;
import com.mobisoft.mobisoftapi.models.Project;

@Repository
public interface ProductProjectRepository extends JpaRepository<ProductProject, Long> {
	List<ProductProject> findByProject(Project project);
}
