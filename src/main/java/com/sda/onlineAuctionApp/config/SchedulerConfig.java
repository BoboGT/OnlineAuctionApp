package com.sda.onlineAuctionApp.config;

import com.sda.onlineAuctionApp.model.Bid;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.model.User;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedDelay = 5000)
    public void cronJob() {
        List<Product> productList = productRepository.findAllExpiredWithoutWinnersAssigned(LocalDateTime.now());
        System.out.println("We found " + productList.size() + " products eligible for winner assignment.");
        for (Product product : productList) {
            Integer max = 0;
            User winner = null;
            for (Bid bid : product.getBidsList()) {
                if (max < bid.getValue()) {
                    max = bid.getValue();
                    winner = bid.getUser();
                }
            }
            product.setWinner(winner);
            productRepository.save(product);
        }
    }

}
