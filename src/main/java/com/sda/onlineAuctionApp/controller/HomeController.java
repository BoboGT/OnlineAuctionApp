package com.sda.onlineAuctionApp.controller;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
     private ProductService productService;

    @GetMapping("/addItem") // aici e ruta pe care o scriu in pagina web,
    public String getAddItemPage(Model model){ // handler pe ruta addItem, metoda se activeaza cand vine un request
        //Dispatcher Servlet stie ca handlerul asteapta un obiect
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "addItem"; // pagina intoarsa din templates
    }

    @PostMapping("/addItem")
    public String postAddItemPage(ProductDto productDto) {
        System.out.println("Am primit " + productDto);
        productService.add(productDto);
        return "addItem";

    }


}
