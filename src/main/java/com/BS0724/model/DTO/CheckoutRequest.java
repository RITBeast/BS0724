package com.BS0724.model.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class CheckoutRequest {
    String toolCode;
    int rentalDayCount;
    int discountPercent;
    Date checkoutDate;
}
