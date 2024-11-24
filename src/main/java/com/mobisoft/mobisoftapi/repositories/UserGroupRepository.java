package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

}
