package com.BS0724.service;

import com.BS0724.dao.ToolRepository;
import com.BS0724.dao.ToolTypeRepository;
import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CheckoutService {
    @Autowired
    ToolRepository toolRepo;
    @Autowired
    ToolTypeRepository toolTypeRepo;
    private final static Logger LOGGER = Logger.getLogger(CheckoutService.class.getName());
    public RentalAgreement createRentalAgreement(CheckoutRequest checkoutDto){
        LOGGER.log(Level.INFO, "Creating Rental Agreement for " + checkoutDto);

        var rentalAgreement = new RentalAgreement();
        rentalAgreement.setRentalDays(checkoutDto.getRentalDayCount());
        rentalAgreement.setDiscountPercent(checkoutDto.getDiscountPercent());
        rentalAgreement.setCheckoutDate(checkoutDto.getCheckoutDate());


        return rentalAgreement;

    }
}
