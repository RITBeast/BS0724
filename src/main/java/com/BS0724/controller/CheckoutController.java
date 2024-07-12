package com.BS0724.controller;

import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.Exceptions.ToolNotFoundException;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import com.BS0724.service.CheckoutService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    CheckoutService checkoutService;
    @PostMapping
    public RentalAgreement checkout(@Validated @RequestBody CheckoutRequest checkoutData) throws ValidationException  {
        System.out.println("Checkout Controller passed in: " + checkoutData);
        if(checkoutData.getDiscountPercent() < 0 || checkoutData.getDiscountPercent() > 100) {
            throw new ValidationException("Discount Percent needs to be between 0 and 100");
        }
        if(checkoutData.getRentalDayCount() < 1){
            throw new ValidationException("Rental day count must at least 1 day");
        }
        try {
            return checkoutService.createRentalAgreement(checkoutData);
        } catch (ToolNotFoundException tnfe){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), tnfe.getMessage());
        }

    }
}
