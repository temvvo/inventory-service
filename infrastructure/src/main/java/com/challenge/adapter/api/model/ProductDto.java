package com.challenge.adapter.api.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String serialNumber;
    private String reference;
    private String size;
    private String type;
    private LocalDate productionDate;
}