package com.BS0724.model.Tool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tool_type")
public class ToolType {
    @Id
    @Column(name = "tool_type", nullable = false)
    private String toolType;

    @Column(name = "daily_charge")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    private BigDecimal dailyCharge;

    @Column(name = "weekday_charge", nullable = false)
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    private Boolean weekdayCharge;

    @Column(name = "weekend_charge", nullable = false)
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    private Boolean weekendCharge;

    @Column(name = "holiday_charge", nullable = false)
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    private Boolean holidayCharge;

}