package com.sda.onlineAuctionApp.service;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.model.Category;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void add(ProductDto productDto, MultipartFile multipartFile) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setStartBiddingPrice(Integer.valueOf(productDto.getStartBiddingPrice()));
        product.setCategory(Category.valueOf(productDto.getCategory()));
        product.setEndDateTime(LocalDateTime.parse(productDto.getEndDateTime()));
        try {
            product.setImage(multipartFile.getBytes()); // prindem daca nu incarcam imagine
        } catch (IOException e) {
            e.printStackTrace(); // afisam eroarea
        }
        productRepository.save(product); // merge si salveaza produsul



    }


    public List<ProductDto> getAllProductDtos() {
        List<Product> products = productRepository.findAll(); // luam toate produsele din baza de date

        List<ProductDto> result = new ArrayList<>(); // lista goala de productDtos
        for(Product product: products) { // parcurgem toate produsele
            ProductDto productDto = new ProductDto(); // fixam produsele pe productDtos
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCategory(product.getCategory().name());
            productDto.setStartBiddingPrice(product.getStartBiddingPrice().toString());
            productDto.setEndDateTime(product.getEndDateTime().toString());

            result.add(productDto); // adaugam productDto populat cu fields din product pe lista noastra goala
        }
        return result; // returnam lista

    }
}
