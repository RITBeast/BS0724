package com.BS0724.service;


import com.BS0724.dao.ToolRepository;
import com.BS0724.dao.ToolTypeRepository;
import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.Exceptions.ToolNotFoundException;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import com.BS0724.model.Tool.Tool;
import com.BS0724.model.Tool.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {
    @InjectMocks
    CheckoutService checkoutService;

    @Mock
    ToolRepository toolRepository;
    @Mock
    ToolTypeRepository toolTypeRepository;
    @BeforeEach
    void setUp() {
    }

    void setupMocks(CheckoutRequest checkoutReq, String toolTypeId){
        checkoutService.toolRepo = toolRepository;
        checkoutService.toolTypeRepo = toolTypeRepository;
        when(toolRepository.findById(checkoutReq.getToolCode())).thenReturn(this.getTool(checkoutReq.getToolCode()));
        when(toolTypeRepository.findById(toolTypeId)).thenReturn(this.getToolType(toolTypeId));
    }
    @Test
    void createRentalAgreement2() throws ToolNotFoundException {

        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Ladder";
        checkoutReq.setToolCode("LADW");
        checkoutReq.setCheckoutDate(LocalDate.of(2020,7,2));
        checkoutReq.setRentalDayCount(3);
        checkoutReq.setDiscountPercent(10);

        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("0.60")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("5.97")));
        assert(returnedRentalAgreement.getChargeDays() == 3);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("1.99")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("5.37")));
        returnedRentalAgreement.printRentalAgreement();
    }
    @Test
    void createRentalAgreement3() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Chainsaw";
        checkoutReq.setToolCode("CHNS");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,7,2));
        checkoutReq.setRentalDayCount(5);
        checkoutReq.setDiscountPercent(25);
        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("1.12")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("4.47")));
        assert(returnedRentalAgreement.getChargeDays() == 3);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("1.49")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("3.35")));
        returnedRentalAgreement.printRentalAgreement();
    }
    @Test
    void createRentalAgreement4() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Jackhammer";
        checkoutReq.setToolCode("JAKD");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,9,3));
        checkoutReq.setRentalDayCount(6);
        checkoutReq.setDiscountPercent(0);

        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("0.00")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("11.96")));
        assert(returnedRentalAgreement.getChargeDays() == 4);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("2.99")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("11.96")));
        returnedRentalAgreement.printRentalAgreement();
    }
    @Test
    void createRentalAgreement5() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Jackhammer";
        checkoutReq.setToolCode("JAKR");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,7,2));
        checkoutReq.setRentalDayCount(9);
        checkoutReq.setDiscountPercent(0);

        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("0.00")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("20.93")));
        assert(returnedRentalAgreement.getChargeDays() == 7);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("2.99")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("20.93")));
        returnedRentalAgreement.printRentalAgreement();
    }
    @Test
    void createRentalAgreement6() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Jackhammer";
        checkoutReq.setToolCode("JAKR");
        checkoutReq.setCheckoutDate(LocalDate.of(2020,7,2));
        checkoutReq.setRentalDayCount(4);
        checkoutReq.setDiscountPercent(50);

        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("2.99")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("5.98")));
        assert(returnedRentalAgreement.getChargeDays() == 2);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("2.99")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("2.99")));
        returnedRentalAgreement.printRentalAgreement();
    }
    //Additional Tests
    @Test
    void createRentalAgreement7() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Hammer";
        checkoutReq.setToolCode("HMMR");
        checkoutReq.setCheckoutDate(LocalDate.of(2020,7,2));
        checkoutReq.setRentalDayCount(367);
        checkoutReq.setDiscountPercent(0);

        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("0.00")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("0.00")));
        assert(returnedRentalAgreement.getChargeDays() == 0);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("10.00")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("0.00")));
        returnedRentalAgreement.printRentalAgreement();
    }
    @Test
    void createRentalAgreement8() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        String toolTypeId = "Ladder";
        checkoutReq.setToolCode("LADW");
        checkoutReq.setCheckoutDate(LocalDate.of(2020,7,4));
        checkoutReq.setRentalDayCount(365);
        checkoutReq.setDiscountPercent(0);

        setupMocks(checkoutReq, toolTypeId);

        var returnedRentalAgreement = checkoutService.createRentalAgreement(checkoutReq);
        checkKnownDataWhenValid(returnedRentalAgreement, checkoutReq);
        assert(returnedRentalAgreement.getDiscountAmount().equals(new BigDecimal("0.00")));
        assert(returnedRentalAgreement.getPreDiscountCharge().equals(new BigDecimal("720.38")));
        assert(returnedRentalAgreement.getChargeDays() == 362);
        assert(returnedRentalAgreement.getDailyRentalCharge().equals(new BigDecimal("1.99")));
        assert(returnedRentalAgreement.getFinalCharge().equals(new BigDecimal("720.38")));
        returnedRentalAgreement.printRentalAgreement();
    }

    private Optional<Tool> getTool(String id){
        Tool toReturn = new Tool();

        switch (id) {
            case "CHNS" -> {
                toReturn.setToolCode(id);
                toReturn.setToolType("Chainsaw");
                toReturn.setBrand("Stihl");
            }
            case "LADW" -> {
                toReturn.setToolCode(id);
                toReturn.setToolType("Ladder");
                toReturn.setBrand("Werner");
            }
            case "JAKD" -> {
                toReturn.setToolCode(id);
                toReturn.setToolType("Jackhammer");
                toReturn.setBrand("Dewalt");
            }
            case "JAKR" -> {
                toReturn.setToolCode(id);
                toReturn.setToolType("Jackhammer");
                toReturn.setBrand("Ridgid");
            }
            case "HMMR" -> {
                toReturn.setToolCode(id);
                toReturn.setToolType("Hammer");
                toReturn.setBrand("Ladeda");
            }
        }
        return Optional.of(toReturn);


    }
    private Optional<ToolType> getToolType(String typeId){
        ToolType toReturn = new ToolType();

        switch (typeId) {
            case "Ladder" -> {
                toReturn.setToolType(typeId);
                toReturn.setDailyCharge(new BigDecimal("1.99"));
                toReturn.setHolidayCharge(false);
                toReturn.setWeekdayCharge(true);
                toReturn.setWeekendCharge(true);
            }
            case "Chainsaw" -> {
                toReturn.setToolType(typeId);
                toReturn.setDailyCharge(new BigDecimal("1.49"));
                toReturn.setHolidayCharge(true);
                toReturn.setWeekdayCharge(true);
                toReturn.setWeekendCharge(false);
            }
            case "Jackhammer" -> {
                toReturn.setToolType(typeId);
                toReturn.setDailyCharge(new BigDecimal("2.99"));
                toReturn.setHolidayCharge(false);
                toReturn.setWeekdayCharge(true);
                toReturn.setWeekendCharge(false);
            }
            case "Hammer" -> {
                toReturn.setToolType(typeId);
                toReturn.setDailyCharge(new BigDecimal("10"));
                toReturn.setHolidayCharge(true);
                toReturn.setWeekdayCharge(false);
                toReturn.setWeekendCharge(false);
            }
        }
        return Optional.of(toReturn);
    }
    private void checkKnownDataWhenValid(RentalAgreement returnedRentalAgreement, CheckoutRequest checkoutReq){
        assert(returnedRentalAgreement.getTool().getToolCode().equals(checkoutReq.getToolCode()));
        assert(returnedRentalAgreement.getDiscountPercent() == checkoutReq.getDiscountPercent());
        assert(returnedRentalAgreement.getCheckoutDate().equals(checkoutReq.getCheckoutDate()));
        assert(returnedRentalAgreement.getRentalDays() == checkoutReq.getRentalDayCount());
    }
}