package com.ecommerce.kitchentreats.db.repsoitories;

import com.ecommerce.kitchentreats.db.pojos.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    Optional<Product> findBySlug(String slug);

}
