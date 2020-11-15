package com.ecommerce.kitchentreats.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pages")
public class AdminController {

    public String index() {
        return "admin/pages/index";
    }

}
