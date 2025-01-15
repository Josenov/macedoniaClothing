package com.macedonia.macedonia.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name="type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name="clothing_id", foreignKey = @ForeignKey(name = "FK_clothing_transaction", value = ConstraintMode.CONSTRAINT))
    private Clothing clothing;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;

    @Column(name="amount", nullable = false)
    private double amount;

    @Column(name="quantity", nullable = false)
    private int quantity;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name="notes")
    private String notes;
}
