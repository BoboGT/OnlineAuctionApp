package com.sda.onlineAuctionApp.mapper;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.model.Category;
import com.sda.onlineAuctionApp.model.Product;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ProductMapper {

    public Product map(ProductDto productDto, MultipartFile multipartFile) {

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

        return product;

    }

    public ProductDto map(Product product) {

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId().toString());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().name());
        productDto.setStartBiddingPrice(product.getStartBiddingPrice().toString());
        productDto.setEndDateTime(product.getEndDateTime().toString());

        String imageAsString = Base64.encodeBase64String(product.getImage());
        productDto.setImage(imageAsString);

        return productDto;


    }
}
