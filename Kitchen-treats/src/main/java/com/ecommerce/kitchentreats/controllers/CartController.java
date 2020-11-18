package com.ecommerce.kitchentreats.controllers;

import com.ecommerce.kitchentreats.domain.pojos.Cart;
import com.ecommerce.kitchentreats.domain.pojos.Product;
import com.ecommerce.kitchentreats.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id, HttpSession session, Model model) {

        boolean isCartActive = session.getAttribute("cart") != null;

        Product product = productRepository.getOne(id);

        if (!isCartActive) {
            HashMap<Integer, Cart> cart = new HashMap<>();
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
            /**TODO: Dirty hack. Remove when refining the code */
            cart.get(id).setTotal(1 * Double.parseDouble(product.getPrice()));
            session.setAttribute("cart", cart);
        } else {
            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
            if (cart.containsKey(id)) {
                int qty = cart.get(id).getQuantity();
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), qty++, product.getImage()));
                /**TODO: Dirty hack. Remove when refining the code */
                cart.get(id).setTotal(qty * Double.parseDouble(product.getPrice()));
                session.setAttribute("cart", cart);
            }

        }

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");

        int qty = 0;
        double total = 0;

        for (Cart value : cart.values()) {
            qty += value.getQuantity();
            total += value.getTotal();
        }

        model.addAttribute("qty", qty);
        model.addAttribute("total", total);

        return "cart_view";

    }

}
