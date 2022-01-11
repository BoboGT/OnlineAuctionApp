package com.sda.onlineAuctionApp.repository;

import com.sda.onlineAuctionApp.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Integer> {


}
