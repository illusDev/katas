package com.ecommerce.kitchentreats.domain.repositories;

import com.ecommerce.kitchentreats.domain.pojos.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    Optional<Category> findBySlug(String slug);


}
