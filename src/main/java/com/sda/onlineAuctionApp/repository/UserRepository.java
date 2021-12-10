package com.sda.onlineAuctionApp.repository;

import com.sda.onlineAuctionApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// parametrii sunt tipul entitatii si tipul id-ului entitatii

// orice metoda din aceasta interfata este completata de Spring si transformata in SQL Queries
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional <User> findByEmail(String email);


}
