package com.ecommerce.kitchentreats.controllers;


import com.ecommerce.kitchentreats.domain.pojos.Category;
import com.ecommerce.kitchentreats.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexPageController {

    @Autowired
    CategoryRepository categoryRepository;

    //main site
    @GetMapping("/")
    public String home(Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "index";
    }

    //Admin portal
    @GetMapping("/admin")
    public String admin(Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "admin/index";
    }
}
