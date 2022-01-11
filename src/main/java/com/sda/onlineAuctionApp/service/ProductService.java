package com.sda.onlineAuctionApp.service;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.mapper.ProductMapper;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;


    public void add(ProductDto productDto, MultipartFile multipartFile) {
        Product product = productMapper.map(productDto, multipartFile);
        productRepository.save(product); // merge si salveaza produsul

    }

    public List<ProductDto> getAllProductDtos(String email) {
        List<Product> products = productRepository.findAll(); // aduce toate produsele din baza de date
        List<ProductDto> result = new ArrayList<>(); // lista goala de productDtos

        for(Product product: products) { // parcurgem toate produsele
            ProductDto productDto = productMapper.map(product, email);
            result.add(productDto); // adaugam productDto populat cu fields din product pe lista noastra goala
        }
        return result; // returnam lista

    }

    public List<ProductDto> getAllActiveProductDtos(String email) {
        List<Product> products = productRepository.findAllByEndDateTimeAfter(LocalDateTime.now());
        List<ProductDto> result = new ArrayList<>(); // lista goala de productDtos

        for (Product product : products) { // parcurgem toate produsele
            ProductDto productDto = productMapper.map(product, email);

            result.add(productDto);
        }
        return result;
    }

    //    public List<ProductDto> getAllProductDtosWithStream() {
//        List<Product> products = productRepository.findAll(); // aduce toate produsele din baza de date
//       return products.stream()
//                .map(productMapper:: map)
//                .collect(Collectors.toList());
//
//    }

    public Optional<ProductDto> getProductDtoById(String productId, String email) { // am stabilit ca metoda intoarce o cutie, care poate contine un Id poate nu
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId)); // cand apelam metoda findById, intoarce un Optional, daca nu ar gasi produsul si nu am da optional, probabil ar arunca NULL Pointer exception
            if(!optionalProduct.isPresent()) {
                return Optional.empty(); // daca nu gaseste id-ul, intoarce containerul gol
            }

           Product product = optionalProduct.get(); // ne aduce produsul 100%, mai jos fixam produsul pe productDto
        ProductDto productDto = productMapper.map(product, email);

        return Optional.of(productDto); // intoarcem o cutie care contine un productDto populat

    }


}
