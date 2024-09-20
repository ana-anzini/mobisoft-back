package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobisoft.mobisoftapi.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
