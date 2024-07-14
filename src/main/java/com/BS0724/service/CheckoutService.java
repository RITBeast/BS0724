package com.BS0724.service;

import com.BS0724.dao.ToolRepository;
import com.BS0724.dao.ToolTypeRepository;
import com.BS0724.model.DTO.CheckoutRequest;
import com.BS0724.model.Exceptions.ToolNotFoundException;
import com.BS0724.model.RentalAgreement.RentalAgreement;
import com.BS0724.model.Tool.ToolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        //might want to set this up to ignore case
        var tool = toolRepo.findById(checkoutDto.getToolCode());
        if(tool.isEmpty()){
            throw new ToolNotFoundException(checkoutDto.getToolCode() + " is not available to rent");
        }
        rentalAgreement.setTool(tool.get());
        //might want to set this up to ignore case
        var toolTypeOpt = toolTypeRepo.findById(rentalAgreement.getTool().getToolType());
        if(toolTypeOpt.isEmpty()){
            throw new ToolNotFoundException(checkoutDto.getToolCode() + " is not available to rent due to a configuration error");
        }
        var toolType = toolTypeOpt.get();
        rentalAgreement.setDueDate(calculateDueDate(checkoutDto.getCheckoutDate(), checkoutDto.getRentalDayCount()));
        rentalAgreement.setDailyRentalCharge(toolType.getDailyCharge().setScale(2, RoundingMode.HALF_EVEN));
        rentalAgreement.setChargeDays(rentalAgreement.getRentalDays() - calculateChargeDaysToRemove(toolType, checkoutDto.getCheckoutDate(), rentalAgreement.getDueDate()));
        rentalAgreement.setPreDiscountCharge(rentalAgreement.getDailyRentalCharge().multiply(new BigDecimal(rentalAgreement.getChargeDays())).setScale(2, RoundingMode.HALF_EVEN));
        rentalAgreement.setDiscountAmount(calculateDiscountAmount(rentalAgreement.getPreDiscountCharge(),rentalAgreement.getDiscountPercent()));
        rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge().subtract(rentalAgreement.getDiscountAmount()));
        return rentalAgreement;

    }

    private int calculateChargeDaysToRemove(ToolType toolType, LocalDate checkoutDate, LocalDate dueDate) {
        int daysToRemove = 0;
        if(!toolType.getHolidayCharge()){
            daysToRemove += calculateHolidaysDuringRental(checkoutDate, dueDate);
        }
        var listOfDaysOfWeekToExclude = new ArrayList<DayOfWeek>();
        if(!toolType.getWeekendCharge()){
            listOfDaysOfWeekToExclude.add(DayOfWeek.SATURDAY);
            listOfDaysOfWeekToExclude.add(DayOfWeek.SUNDAY);

        }
        if(!toolType.getWeekdayCharge()){
            listOfDaysOfWeekToExclude.add(DayOfWeek.MONDAY);
            listOfDaysOfWeekToExclude.add(DayOfWeek.TUESDAY);
            listOfDaysOfWeekToExclude.add(DayOfWeek.WEDNESDAY);
            listOfDaysOfWeekToExclude.add(DayOfWeek.THURSDAY);
            listOfDaysOfWeekToExclude.add(DayOfWeek.FRIDAY);
        }
        if(!CollectionUtils.isEmpty(listOfDaysOfWeekToExclude)) {
            daysToRemove += calculateDaysOfWeekRemovalDuringRental(checkoutDate, dueDate, listOfDaysOfWeekToExclude);
        }
        return daysToRemove;
    }
    //A separate date service would be in order if we were to expand this class at all
    private int calculateDaysOfWeekRemovalDuringRental(LocalDate checkoutDate, LocalDate dueDate, List<DayOfWeek> daysToExclude) {
        int daysToRemove = 0;
        var dateToProcess = checkoutDate;
        while(dateToProcess.isBefore(dueDate)){
            if(daysToExclude.contains(dateToProcess.getDayOfWeek())){
                daysToRemove++;
            }
            dateToProcess = dateToProcess.plusDays(1);
        }
        return daysToRemove;

    }

    private int calculateHolidaysDuringRental(LocalDate checkoutDate, LocalDate dueDate) {

        List<LocalDate> datesToCheck = new ArrayList<>();
        for(int i = checkoutDate.getYear(); i <= dueDate.getYear(); i++){
            datesToCheck.addAll(getHolidaysToCheckForYear(i));
        }
        //dueDate + 1 & checkoutdate -1 to cover edge case of rental starting or ending on a holiday
        //I would hope that we aren't
        return (int) datesToCheck.stream().filter(holiday -> holiday.isBefore(dueDate.plusDays(1)) && holiday.isAfter(checkoutDate.minusDays(1))).count();

    }

    private List<LocalDate> getHolidaysToCheckForYear(int year) {
        ArrayList<LocalDate> datesToCheck = new ArrayList<>();
        //Fourth of July
        datesToCheck.add(LocalDate.of(year, 7, 4));
        datesToCheck.add(getLaborDay(year));
        return datesToCheck;
    }

    private LocalDate getLaborDay(int year) {
        var toReturn = LocalDate.of(year, 9, 1);
        while(toReturn.getDayOfWeek() != DayOfWeek.MONDAY){
            toReturn = toReturn.plusDays(1);
        }
        return toReturn;
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDayCount) {

        return checkoutDate.plusDays(rentalDayCount);
    }

    private BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, int discountPercent) {
        return preDiscountCharge.multiply(new BigDecimal(discountPercent)).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN);
    }

}
