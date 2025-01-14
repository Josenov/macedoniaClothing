package com.macedonia.macedonia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;

    private String type;

    private String clothingName;


    private String storeName;

    private String storeLocation;

    private Long clothingId; // Relación con Clothing

    private Long storeId; // Relación con Store

    private double amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate; // Fecha de la transacción

    private String notes;
}
