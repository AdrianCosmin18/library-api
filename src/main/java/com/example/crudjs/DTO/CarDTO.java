package com.example.crudjs.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarDTO {

    private String brand;
    private String model;
    private Double weight;
    private Boolean isAvailable;
}
