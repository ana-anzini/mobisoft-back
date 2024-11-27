package com.mobisoft.mobisoftapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByCategory(Category category);
	
	List<Product> findByUserGroupId(Long userGroupId);
}
