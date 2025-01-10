package com.macedonia.macedonia.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClothingDTO {
    private Long id;
    private String name;
    private String size;
    private String color;
    private double purchasePrice;
    private double sellingPrice;
    private Integer stock;
    private Long inventoryId;
}
