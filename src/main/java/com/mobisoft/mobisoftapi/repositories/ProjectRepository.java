package com.mobisoft.mobisoftapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

	List<Project> findByUserGroupId(Long userGroupId);
}
