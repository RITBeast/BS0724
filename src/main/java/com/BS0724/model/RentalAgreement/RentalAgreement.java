package com.BS0724.model.RentalAgreement;


import com.BS0724.model.Tool.Tool;

import java.math.BigDecimal;
import java.util.Date;

public class RentalAgreement {
    Tool tool;
    int rentalDays;
    Date checkoutDate;
    Date dueDate;
    BigDecimal dailyRentalCharge;
    int chargeDays;  //count of chargeable days (excludes no-charge days)

    BigDecimal preDiscountCharge;
    int discountPercent;
    BigDecimal discountAmount;
    BigDecimal finalCharge;

}
