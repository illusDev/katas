package com.ecommerce.kitchentreats.domain.repositories;

import com.ecommerce.kitchentreats.domain.pojos.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    Optional<Product> findBySlug(String slug);

    List<Product> findAllByCategoryId(int categoryId);

}
