package com.BS0724.service;

import com.BS0724.dao.ToolRepository;
import com.BS0724.dao.ToolTypeRepository;
import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    @Autowired
    ToolRepository toolRepo;
    @Autowired
    ToolTypeRepository toolTypeRepo;
    public RentalAgreement createRentalAgreement(CheckoutRequest checkoutDto){

        return new RentalAgreement();

    }
}
