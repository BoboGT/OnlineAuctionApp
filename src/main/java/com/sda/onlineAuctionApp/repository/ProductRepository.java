package com.sda.onlineAuctionApp.repository;

import com.sda.onlineAuctionApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
// spring stie ca tre sa il caute ca nume de camp si face un query in baza de date, aceste metode se fac cu autocomplete
 List<Product> findAllByEndDateTimeAfter(LocalDateTime localDateTime);

 //aici ne folosim de Hybernate Query Language
 @Query("select p from Product p where p.endDateTime < :now and p.winner is null and p.bidsList.size > 0")
 List<Product> findAllExpiredWithoutWinnersAssigned(@Param("now") LocalDateTime now);

 @Query("select p from Product p where p.winner.email = :email")
 List<Product> findByWinnerEmail(@Param("email") String email);
}
