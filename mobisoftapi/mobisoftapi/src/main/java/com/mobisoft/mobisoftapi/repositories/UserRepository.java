package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobisoft.mobisoftapi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
