package com.BS0724.model.Tool;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tool")
public class Tool {
    @Id
    @Column(name = "tool_code", nullable = false)
    private String toolCode;

    @Column(name = "tool_type", nullable = false)
    private String toolType;

    @Column(name = "brand", nullable = false)
    private String brand;

}