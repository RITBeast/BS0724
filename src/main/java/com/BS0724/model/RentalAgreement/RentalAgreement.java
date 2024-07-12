package com.BS0724.model.RentalAgreement;


import com.BS0724.model.Tool.Tool;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalAgreement {
    Tool tool;
    int rentalDays;

    LocalDate checkoutDate;
    LocalDate dueDate;
    BigDecimal dailyRentalCharge;
    int chargeDays;  //count of chargeable days (excludes no-charge days)

    BigDecimal preDiscountCharge;
    int discountPercent;
    BigDecimal discountAmount;
    BigDecimal finalCharge;

}
