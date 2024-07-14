package com.BS0724.model.RentalAgreement;


import com.BS0724.model.Tool.Tool;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public void printRentalAgreement(){
        final String NEWLINE = "\n";
        final String CURRENCY = "$";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        String sb = "Tool code: " + tool.getToolCode() + NEWLINE +
                "Tool type: " + tool.getToolType() + NEWLINE +
                "Tool brand: " + tool.getBrand() + NEWLINE +
                "Rental days: " + rentalDays + NEWLINE +
                "Check out date: " + checkoutDate.format(formatter) + NEWLINE +
                "Due date: " + dueDate.format(formatter) + NEWLINE +
                "Daily rental charge: " + CURRENCY + dailyRentalCharge + NEWLINE +
                "Charge days: " + chargeDays + NEWLINE +
                "Pre-discount charge: " + CURRENCY + preDiscountCharge + NEWLINE +
                "Discount percent: " + discountPercent + "%" + NEWLINE +
                "Discount amount: " + CURRENCY + discountAmount + NEWLINE +
                "Final charge: " + CURRENCY + finalCharge + NEWLINE;

        System.out.println(sb);
    }
}
