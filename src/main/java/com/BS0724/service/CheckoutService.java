package com.BS0724.service;

import com.BS0724.dao.ToolRepository;
import com.BS0724.dao.ToolTypeRepository;
import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.Exceptions.ToolNotFoundException;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CheckoutService {
    @Autowired
    ToolRepository toolRepo;
    @Autowired
    ToolTypeRepository toolTypeRepo;
    private final static Logger LOGGER = Logger.getLogger(CheckoutService.class.getName());
    public RentalAgreement createRentalAgreement(CheckoutRequest checkoutDto) throws ToolNotFoundException {
        LOGGER.log(Level.INFO, "Creating Rental Agreement for " + checkoutDto);

        var rentalAgreement = new RentalAgreement();
        rentalAgreement.setRentalDays(checkoutDto.getRentalDayCount());
        rentalAgreement.setDiscountPercent(checkoutDto.getDiscountPercent());
        rentalAgreement.setCheckoutDate(checkoutDto.getCheckoutDate());
        var tool = toolRepo.findById(checkoutDto.getToolCode());
        if(tool.isEmpty()){
            throw new ToolNotFoundException(checkoutDto.getToolCode() + " is not available to rent");
        }
        rentalAgreement.setTool(tool.get());
        var toolTypeOpt = toolTypeRepo.findById(rentalAgreement.getTool().getToolType());
        if(toolTypeOpt.isEmpty()){
            throw new ToolNotFoundException(checkoutDto.getToolCode() + " is not available to rent due to a configuration error");
        }
        var toolType = toolTypeOpt.get();
        rentalAgreement.setDueDate(calculateDueDate(checkoutDto.getCheckoutDate(), checkoutDto.getRentalDayCount()));
        rentalAgreement.setDailyRentalCharge(toolType.getDailyCharge());

        return rentalAgreement;

    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDayCount) {

        return checkoutDate.plusDays(rentalDayCount);
    }
}
