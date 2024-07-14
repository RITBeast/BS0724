package com.BS0724.controller;

import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.Exceptions.ToolNotFoundException;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import com.BS0724.model.Tool.Tool;
import com.BS0724.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {
    @InjectMocks
    CheckoutController checkoutController;

    @Mock
    CheckoutService checkoutService = new CheckoutService();

    @BeforeEach
    void setUp() {
        checkoutController = new CheckoutController();
    }


    @Test
    void checkoutTest1() {
        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("JAKR");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,9,3));
        checkoutReq.setRentalDayCount(5);
        checkoutReq.setDiscountPercent(101);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> checkoutController.checkout(checkoutReq));
        assertEquals("400 BAD_REQUEST \"Discount Percent needs to be between 0 and 100\"", exception.getMessage());

    }


    @Test
    void checkoutTest2() throws ToolNotFoundException {

        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("LADW");
        checkoutReq.setCheckoutDate(LocalDate.of(2020,7,2));
        checkoutReq.setRentalDayCount(3);
        checkoutReq.setDiscountPercent(10);
        checkoutController.checkoutService = checkoutService;
        when(checkoutService.createRentalAgreement(checkoutReq)).thenReturn(buildRentalAgreementForTest(checkoutReq));

        var returnedRentalAgreement = checkoutController.checkout(checkoutReq);

        checkMockedDataWhenValid(returnedRentalAgreement, checkoutReq);

    }
    @Test
    void checkoutTest3() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("CHNS");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,7,2));
        checkoutReq.setRentalDayCount(5);
        checkoutReq.setDiscountPercent(25);
        checkoutController.checkoutService = checkoutService;
        when(checkoutService.createRentalAgreement(checkoutReq)).thenReturn(buildRentalAgreementForTest(checkoutReq));

        var returnedRentalAgreement = checkoutController.checkout(checkoutReq);
        checkMockedDataWhenValid(returnedRentalAgreement, checkoutReq);

    }
    @Test
    void checkoutTest4() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("JAKD");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,9,3));
        checkoutReq.setRentalDayCount(6);
        checkoutReq.setDiscountPercent(0);

        checkoutController.checkoutService = checkoutService;
        when(checkoutService.createRentalAgreement(checkoutReq)).thenReturn(buildRentalAgreementForTest(checkoutReq));

        var returnedRentalAgreement = checkoutController.checkout(checkoutReq);
        checkMockedDataWhenValid(returnedRentalAgreement, checkoutReq);

    }
    @Test
    void checkoutTest5() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("JAKR");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,7,2));
        checkoutReq.setRentalDayCount(9);
        checkoutReq.setDiscountPercent(0);

        checkoutController.checkoutService = checkoutService;
        when(checkoutService.createRentalAgreement(checkoutReq)).thenReturn(buildRentalAgreementForTest(checkoutReq));

        var returnedRentalAgreement = checkoutController.checkout(checkoutReq);
        checkMockedDataWhenValid(returnedRentalAgreement, checkoutReq);

    }
    @Test
    void checkoutTest6() throws ToolNotFoundException {
        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("JAKR");
        checkoutReq.setCheckoutDate(LocalDate.of(2020,7,2));
        checkoutReq.setRentalDayCount(4);
        checkoutReq.setDiscountPercent(50);

        checkoutController.checkoutService = checkoutService;
        when(checkoutService.createRentalAgreement(checkoutReq)).thenReturn(buildRentalAgreementForTest(checkoutReq));

        var returnedRentalAgreement = checkoutController.checkout(checkoutReq);
        checkMockedDataWhenValid(returnedRentalAgreement, checkoutReq);

    }
    //Additional Tests
    @Test
    void checkoutTest7() {
        var checkoutReq = new CheckoutRequest();
        checkoutReq.setToolCode("JAKR");
        checkoutReq.setCheckoutDate(LocalDate.of(2015,9,3));
        checkoutReq.setRentalDayCount(-1);
        checkoutReq.setDiscountPercent(0);

        Throwable exception = assertThrows(ResponseStatusException.class, () -> checkoutController.checkout(checkoutReq));
        assertEquals("400 BAD_REQUEST \"Rental day count must at least 1 day\"", exception.getMessage());


    }
    private void checkMockedDataWhenValid(RentalAgreement returnedRentalAgreement, CheckoutRequest checkoutReq){
        assert(returnedRentalAgreement.getTool().getToolCode().equals(checkoutReq.getToolCode()));
        assert(returnedRentalAgreement.getDiscountPercent() == checkoutReq.getDiscountPercent());
        assert(returnedRentalAgreement.getCheckoutDate().equals(checkoutReq.getCheckoutDate()));
        assert(returnedRentalAgreement.getRentalDays() == checkoutReq.getRentalDayCount());
    }
    private RentalAgreement buildRentalAgreementForTest(CheckoutRequest cr){
        RentalAgreement ra = new RentalAgreement();
        var tool = new Tool();
        tool.setToolCode(cr.getToolCode());
        ra.setTool(tool);
        ra.setDiscountPercent(cr.getDiscountPercent());
        ra.setRentalDays(cr.getRentalDayCount());
        ra.setCheckoutDate(cr.getCheckoutDate());

        return ra;
    }


}