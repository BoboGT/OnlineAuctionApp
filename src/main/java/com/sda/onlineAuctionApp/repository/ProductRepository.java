package com.sda.onlineAuctionApp.repository;

import com.sda.onlineAuctionApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {



}
