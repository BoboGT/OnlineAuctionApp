package com.sda.onlineAuctionApp.service;

import com.sda.onlineAuctionApp.dto.BidDto;
import com.sda.onlineAuctionApp.mapper.BidMapper;
import com.sda.onlineAuctionApp.model.Bid;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.model.User;
import com.sda.onlineAuctionApp.repository.BidRepository;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import com.sda.onlineAuctionApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidService {


    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidMapper bidMapper;

    public void placeBid(BidDto bidDto, String productId, String userEmail) {
//        System.out.println("Adaug valoarea " + bidDto.getValue() + " pentru produsul cu id-ul " + productId
//         + " si utilizatorul cu emailul " + userEmail);

        Bid bid = bidMapper.map(bidDto, productId, userEmail);
        bidRepository.save(bid);

    }
}
