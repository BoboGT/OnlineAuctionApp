package com.sda.onlineAuctionApp.validator;

import com.sda.onlineAuctionApp.dto.BidDto;
import com.sda.onlineAuctionApp.model.Bid;
import com.sda.onlineAuctionApp.model.Product;
import com.sda.onlineAuctionApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
public class BidDtoValidator {

    @Autowired
    private ProductRepository productRepository;

    public void validate(BidDto bidDto, BindingResult bindingResult, String productId) {
        String bidValue = bidDto.getValue();
        //1 sa nu fie gol
        // 2 sa fie numar pozitiv
        // 4 sa fie mai mare ca cel mai mare bid (in cazul in care sunt bids)
        // 3 sa fie mai mare sau egal ca starting price

        // 1
        if(bidValue.isEmpty()) {
            FieldError fieldError = new FieldError("bidDto", "value", "Value should not be empty!");
            bindingResult.addError(fieldError);
            return;
        }

        //2
        Integer bidValueAsNumber = 0;
        try{
         bidValueAsNumber = Integer.valueOf(bidValue);
        }
        catch (NumberFormatException numberFormatException) {
            FieldError fieldError = new FieldError("bidDto", "value", "Value should be a number!");
            bindingResult.addError(fieldError);
            return;
        }

        if(bidValueAsNumber <= 0) {
            FieldError fieldError = new FieldError("bidDto", "value", "Value should be positive!");
            bindingResult.addError(fieldError);
            return;
        }
        // 3
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId));
        if(!optionalProduct.isPresent()) {
            FieldError fieldError = new FieldError("bidDto", "value", "Product id is not valid!");
            bindingResult.addError(fieldError);
            return; // il punem ca sa ne scoata din if
        }
        Product product = optionalProduct.get(); // instructiune de "despachetare a optionalului"
        if(product.getStartBiddingPrice() > bidValueAsNumber) {
            FieldError fieldError = new FieldError("bidDto", "value", "Value must at least starting price!");
            bindingResult.addError(fieldError);
            return;
        }

        // 4
        List<Bid> bidList = product.getBidsList();
        if(!bidList.isEmpty()) {
            int max = 0;
            for(Bid bid: bidList) {
                if(max < bid.getValue()) {
                    max = bid.getValue();
                }
            }
            if(bidValueAsNumber <= max) {
                FieldError fieldError = new FieldError("bidDto", "value", "Value must greater than latest bid!");
                bindingResult.addError(fieldError);
                return;
            }
        }



    }
}
