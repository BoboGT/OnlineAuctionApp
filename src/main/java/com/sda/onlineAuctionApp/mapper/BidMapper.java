package com.sda.onlineAuctionApp.mapper;

import com.sda.onlineAuctionApp.dto.BidDto;
import com.sda.onlineAuctionApp.model.Bid;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.model.User;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import com.sda.onlineAuctionApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidMapper {
    // construim un bid pe baza unui BidDto ca sa putem sa o folosim in service mai optimizat

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Bid map(BidDto bidDto, String productId, String userEmail) {

        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId));
        if(!optionalProduct.isPresent()){
            throw new RuntimeException("Product id" + productId + " is not valid"); // throw new e ca si cum ai spune return exception, iti iese din metoda
        }

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if(!optionalUser.isPresent()) {
            throw new RuntimeException("User with email " + userEmail + "is not registered");
        }

        Bid bid = new Bid();
        bid.setValue(Integer.valueOf(bidDto.getValue()));
        bid.setProduct(optionalProduct.get());
        bid.setUser(optionalUser.get());

        return bid;
    }
}
