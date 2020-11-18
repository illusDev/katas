package com.ecommerce.kitchentreats.controllers;

import com.ecommerce.kitchentreats.domain.pojos.Category;
import com.ecommerce.kitchentreats.domain.pojos.Product;
import com.ecommerce.kitchentreats.domain.repositories.CategoryRepository;
import com.ecommerce.kitchentreats.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static final String ALL_SLUG = "all";


    @GetMapping("/{slug}")
    public String getCategory(@PathVariable String slug, Model model) {

        if (slug.equals(ALL_SLUG)) {
            List<Product> products = productRepository.findAll();
            model.addAttribute("products", products);
        } else {

            Optional<Category> category = categoryRepository.findBySlug(slug);

            if (!category.isPresent()) {
                return "redirect:/";
            }


            int categoryId = category.get().getId();
            String categoryName = category.get().getName();
            List<Product> products = productRepository.findAllByCategoryId(categoryId);
            List<Category> categories = categoryRepository.findAll();

            model.addAttribute("products", products);
            model.addAttribute("categories", categories);
            model.addAttribute("categoryName", categoryName);

        }


        return "products";
    }




}
