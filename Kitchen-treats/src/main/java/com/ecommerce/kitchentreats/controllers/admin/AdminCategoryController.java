package com.ecommerce.kitchentreats.controllers.admin;

import com.ecommerce.kitchentreats.domain.pojos.Category;
import com.ecommerce.kitchentreats.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String indexPage(Model model) {

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("categories",categories);

        return "admin/categories/index";
    }

    @GetMapping("/add")
    public String getAddPage(@ModelAttribute Category category) {
        return "admin/categories/add";
    }


    @PostMapping("/add")
    public String postAddPage(@ModelAttribute Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/categories/add";
        }

        String slug = category.getName().toLowerCase().replace(" ",  "-");

        if (!categoryExists(category)) {
            redirectAttributes.addFlashAttribute("message", "Category added");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            category.setSlug(slug);
            categoryRepository.save(category);
        } else {
            redirectAttributes.addFlashAttribute("message", "Category already exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo", category);
        }


        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id, Model model) {


        Category category = categoryRepository.getOne(id);

        model.addAttribute("category", category);

        return "redirect:/admin/categories/edit";
    }

    @PostMapping("/edit")
    public String postUpdate(@ModelAttribute Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        Category currentCategory = categoryRepository.getOne(category.getId());

        String slug = category.getName().toLowerCase().replace(" ", "-");

        if (bindingResult.hasErrors()) {
            model.addAttribute("CategoryName", currentCategory.getName());
            return "/admin/categories/edit";
        }


        if (!categoryExists(category)) {
            redirectAttributes.addFlashAttribute("message", "Category edited");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            category.setSlug(slug);
            categoryRepository.save(category);
        } else {
            redirectAttributes.addFlashAttribute("message", "Category already exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/admin/categories/edit/" + category.getId();

    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes) {

        categoryRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Category deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/categories";

    }

    private boolean categoryExists(Category category) {
        return categoryRepository.findByName(category.getName()).isPresent();
    }

}
