package com.ecommerce.kitchentreats.controllers.admin;

import com.ecommerce.kitchentreats.domain.pojos.Category;
import com.ecommerce.kitchentreats.domain.pojos.Product;
import com.ecommerce.kitchentreats.domain.repositories.CategoryRepository;
import com.ecommerce.kitchentreats.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public String indexPage(Model model) {

        List<Product> products = productRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        HashMap<Integer, String> cats = new HashMap<>();

        for (Category cat : categories) {
            cats.put(cat.getId(), cat.getName());
        }

        model.addAttribute("products", products);
        model.addAttribute("cats", cats);


        return "/admin/products/index";

    }

    @GetMapping("add")
    public String getAddPage(@ModelAttribute Product product, Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String postPage(@ModelAttribute Product product,
                                           BindingResult bindingResult,
                                           Optional<MultipartFile> file,
                                           RedirectAttributes redirectAttributes,
                                           Model model) throws IOException {

        List<Category> categories = categoryRepository.findAll();

        if (bindingResult.hasErrors()) {
            //load categories again
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        if (file.isPresent() && !isValidImageFormat(file.get().getOriginalFilename())) {
            addInvalidImageFlashAttribute(redirectAttributes);
        }

        byte[] bytes = file.get().getBytes();
        Path path = Paths.get("src/main/resources/static/media/" + file.get().getOriginalFilename());
        String slug = product.getName().toLowerCase().replace(" ", "-");

        if (productExists(product)) {
            addProductExistsFlashAttribute(redirectAttributes);
        } else {
            redirectAttributes.addFlashAttribute("message", "Product added");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            product.setSlug(slug);
            product.setImage(file.get().getOriginalFilename());
            productRepository.save(product);
            Files.write(path, bytes);
        }

        return "redirect:/admin/products";
    }



    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id, Model model) {


        Product product = productRepository.getOne(id);
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/products/edit";
    }

    @PostMapping("/edit")
    public String postUpdate(@ModelAttribute Product product,
                                             BindingResult bindingResult,
                                             Optional<MultipartFile> file,
                                             RedirectAttributes redirectAttributes,
                                             Model model) throws IOException {

        Product currentProduct = productRepository.getOne(product.getId());
        List<Category> categories = categoryRepository.findAll();

        String slug = product.getName().toLowerCase().replace(" ", "-");
        byte[] bytes = file.get().getBytes();
        Path path = Paths.get("src/main/resources/static/media/" + file.get().getOriginalFilename());


        if (bindingResult.hasErrors()) {
            model.addAttribute("ProductName", currentProduct.getName());
            model.addAttribute("categories", categories);
            return "/admin/products/edit";
        }


        if (!productExists(product)) {
            redirectAttributes.addFlashAttribute("message", "Product edited");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            product.setSlug(slug);
            product.setImage(file.get().getOriginalFilename());
            productRepository.save(product);
            Files.write(path, bytes);
        } else {
            redirectAttributes.addFlashAttribute("message", "Product already exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/admin/products/edit/";

    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {

        productRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Product deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/products";

    }


    /************************ Helper Methods ************************************/

    private boolean productExists(Product category) {
        return productRepository.findByName(category.getName()).isPresent();
    }

    private boolean isValidImageFormat(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".png");
    }

    private void addInvalidImageFlashAttribute(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Image must be a jpg or png");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
    }

    private void addProductExistsFlashAttribute(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Product already exists, choose another");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
    }

}
