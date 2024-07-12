package com.BS0724.model.DTO;

import lombok.Data;
import java.time.LocalDate;
@Data
public class CheckoutRequest {
    String toolCode;
    int rentalDayCount;
    int discountPercent;
    LocalDate checkoutDate;
}
