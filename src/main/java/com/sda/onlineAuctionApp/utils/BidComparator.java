package com.sda.onlineAuctionApp.utils;

import com.sda.onlineAuctionApp.model.Bid;

import java.util.Comparator;

public class BidComparator implements Comparator<Bid> {


    @Override
    public int compare(Bid bid1, Bid bid2) {
        if(bid1.getValue().compareTo(bid2.getValue()) < 0 ) { // linia 12 la fel cu linia 14
            return -1;
        } else if(bid1.getValue() > bid2.getValue()) {
            return 1;
        }

        return 0;
    }
}
