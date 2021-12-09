package com.sda.onlineAuctionApp.controller;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.dto.UserDto;
import com.sda.onlineAuctionApp.service.ProductService;
import com.sda.onlineAuctionApp.service.UserService;
import com.sda.onlineAuctionApp.validator.ProductDtoValidator;
import com.sda.onlineAuctionApp.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
     private ProductService productService;

    @Autowired
    private ProductDtoValidator productDtoValidator;

    @Autowired
    private UserDtoValidator userDtoValidator;

    @Autowired
    private UserService userService;

    @GetMapping("/addItem") // aici e ruta pe care o scriu in pagina web,
    public String getAddItemPage(Model model){ // handler pe ruta addItem, metoda se activeaza cand vine un request
        //Dispatcher Servlet stie ca handlerul asteapta un obiect
        System.out.println("Rulez get pe /addItem");
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "addItem"; //intoarcem un nume de pagina care da acel formular
    }

    @PostMapping("/addItem")
    public String postAddItemPage(ProductDto productDto, BindingResult bindingResult, @RequestParam("productImage") MultipartFile multipartFile) {
        productDtoValidator.validate(productDto, bindingResult);
        if(bindingResult.hasErrors()) {
            return "addItem"; // returneaza numele de pagina de post
        }

        productService.add(productDto, multipartFile);
        return "redirect:/addItem"; // rulez redirect catre get, nu intoarcem un nume de pagina,
        //intoarcem un get care apeleaza metoda si da in final un nume de pagina

    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<ProductDto> productDtoList = productService.getAllProductDtos();
        model.addAttribute("products", productDtoList);

        return "home";
    }

    @GetMapping("/item/{productId}") // spring intelege ca primeste un parametru cand vede {}
    public String getProductPage(@PathVariable(value = "productId") String productId, Model model) { // captureaza valoarea din url si o storeaza in parametrul nostru
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(productId);
        if(!optionalProductDto.isPresent()) {
            return "errorPage";
        }
        ProductDto productDto = optionalProductDto.get(); // despachetarea cutiei.get
        model.addAttribute("product", productDto);
        return "viewItem";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistrationPage(Model model, UserDto userDto, BindingResult bindingResult) {
        userDtoValidator.validate(userDto, bindingResult);
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        userService.add(userDto);
        return "redirect:/home";


    }


}
