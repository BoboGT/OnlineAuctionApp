package com.sda.onlineAuctionApp.controller;

import com.sda.onlineAuctionApp.dto.BidDto;
import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.dto.UserDto;
import com.sda.onlineAuctionApp.service.BidService;
import com.sda.onlineAuctionApp.service.ProductService;
import com.sda.onlineAuctionApp.service.UserService;
import com.sda.onlineAuctionApp.validator.BidDtoValidator;
import com.sda.onlineAuctionApp.validator.ProductDtoValidator;
import com.sda.onlineAuctionApp.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private BidService bidService;

    @Autowired
    private BidDtoValidator bidDtoValidator;

    @GetMapping("/addItem") // aici e ruta pe care o scriu in pagina web,
    public String getAddItemPage(Model model) { // handler pe ruta addItem, metoda se activeaza cand vine un request
        //Dispatcher Servlet stie ca handlerul asteapta un obiect
        System.out.println("Rulez get pe /addItem");
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "addItem"; //intoarcem un nume de pagina care da acel formular
    }

    @PostMapping("/addItem")
    public String postAddItemPage(ProductDto productDto, BindingResult bindingResult, @RequestParam("productImage") MultipartFile multipartFile) {
        productDtoValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addItem"; // returneaza numele de pagina de post
        }

        productService.add(productDto, multipartFile);
        return "redirect:/addItem"; // rulez redirect catre get, nu intoarcem un nume de pagina,
        //intoarcem un get care apeleaza metoda si da in final un nume de pagina

    }

    @GetMapping({"/home","/"})
    public String getHomePage(Model model, Authentication authentication) {
        List<ProductDto> productDtoList = productService.getAllActiveProductDtos(authentication.getName());
        model.addAttribute("products", productDtoList);

        return "home";
    }

    @GetMapping("/myProducts")
    public String getmyProductsPage(Model model, Authentication authentication) {
        List<ProductDto> productDtoList = productService.getProductDtosFor(authentication.getName());
        model.addAttribute("products", productDtoList);

        return "myProducts";
    }



    @GetMapping("/item/{productId}") // spring intelege ca primeste un parametru cand vede {}
    public String getProductPage(@PathVariable(value = "productId") String productId, Model model, Authentication authentication) { // captureaza valoarea din url si o storeaza in parametrul nostru
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(productId, authentication.getName());
        if (!optionalProductDto.isPresent()) {
            return "errorPage";
        }
        BidDto bidDto = new BidDto();
        model.addAttribute("bidDto", bidDto);
        ProductDto productDto = optionalProductDto.get(); // despachetarea cutiei.get
        model.addAttribute("product", productDto);
        return "viewItem";
    }

    @PostMapping("/item/{productId}")                                                                            // interfeta SSecurity care filtreaza si luam datele user logat
    public String postProductPage(BidDto bidDto, BindingResult bindingResult, @PathVariable(value = "productId") String productId, Authentication authentication, Model model) {
        System.out.println("am primit bid value= " + bidDto.getValue() + " pentru produsul cu id-ul " + productId);
        bidDtoValidator.validate(bidDto, bindingResult, productId);
        if(bindingResult.hasErrors()) {
            Optional<ProductDto> optionalProductDto = productService.getProductDtoById(productId, authentication.getName());
            if (!optionalProductDto.isPresent()) {
                return "errorPage";
            }
           // model.addAttribute("bid", bidDto);
            model.addAttribute("product", optionalProductDto.get());
            return "viewItem";
        }
        bidService.placeBid(bidDto, productId, authentication.getName());
        return "redirect:/item/" + productId;
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
        if (bindingResult.hasErrors()) {
            System.out.println("eroare la validare");
            return "registration";
        }
        userService.add(userDto);
        return "redirect:/home";

    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "login";

    }

    @GetMapping("/login-error")
    public String getLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);

        return "login";

    }


}
