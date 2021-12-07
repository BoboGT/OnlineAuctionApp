package com.sda.onlineAuctionApp.service;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void add(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setStartBiddingPrice(Integer.valueOf(productDto.getStartBiddingPrice()));
      //  product.setCategory();
        product.setEndDateTime(LocalDateTime.parse(productDto.getEndDateTime()));
        productRepository.save(product);



    }


}
