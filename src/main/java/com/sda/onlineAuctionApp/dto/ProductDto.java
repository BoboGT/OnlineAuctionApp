package com.sda.onlineAuctionApp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private String startBiddingPrice;
    private String category;
    private String endDateTime;
    private String image; // inclusiv imaginea va fi un String
    private String currentBidPrice;
    private String loggeduserMaxBid;




}
