package com.sda.onlineAuctionApp.repository;

import com.sda.onlineAuctionApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
// spring stie ca tre sa il caute ca nume de camp si face un query in baza de date, aceste metode se fac cu autocomplete
 List<Product> findAllByEndDateTimeAfter(LocalDateTime localDateTime);

}
