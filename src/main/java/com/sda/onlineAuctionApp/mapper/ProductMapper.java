package com.sda.onlineAuctionApp.mapper;

import com.sda.onlineAuctionApp.dto.ProductDto;
import com.sda.onlineAuctionApp.model.Bid;
import com.sda.onlineAuctionApp.model.Category;
import com.sda.onlineAuctionApp.model.Product;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public ProductDto map(Product product, String email) {

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId().toString());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().name());
        productDto.setStartBiddingPrice(product.getStartBiddingPrice().toString());
        productDto.setEndDateTime(product.getEndDateTime().toString());

        Integer max = getBidMaxValue(product.getBidsList());
        productDto.setCurrentBidPrice(max.toString());

        Integer loggedUserMaxValue = getLoggedUserBiggestBid(product, email);
        productDto.setLoggeduserMaxBid(loggedUserMaxValue.toString());


        String imageAsString = Base64.encodeBase64String(product.getImage());
        productDto.setImage(imageAsString);

        return productDto;


    }
// implementam partea in care afisam bidul maxim al unui User logat
    private Integer getLoggedUserBiggestBid(Product product, String email) {
        List<Bid> bidList = product.getBidsList();
        List<Bid> loggedUserBidList = new ArrayList<>();

        for(Bid bid: bidList) {
            if(bid.getUser().getEmail().equals(email)) { //verificam daca emailul userului este acelasi cu al userului logat
                loggedUserBidList.add(bid);
            }
        }
            Integer max = getBidMaxValue(loggedUserBidList);
        return max;
    }

    private Integer getBidMaxValue(List<Bid> bidList) {
        Integer max = 0;
        for(Bid bid: bidList){
            if(max < bid.getValue()) {
                max = bid.getValue();
            }
        }
        return max;
    }
}
