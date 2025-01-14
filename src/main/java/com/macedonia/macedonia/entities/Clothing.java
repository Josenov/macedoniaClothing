package com.macedonia.macedonia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Clothing")
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "stock")
    private Integer stock = 1;

    @ManyToOne
    @JoinColumn(name="inventory_id", nullable = true)
    @JsonBackReference
    private Inventory inventory;



    // Método getter sincronizado
    public Long getInventoryId() {
        return inventory != null ? inventory.getId() : null;
    }


    /*@Transient
    private Long myInventoryId;
    // Método setter sincronizado
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        this.myInventoryId= (inventory != null) ? inventory.getId() : null;
    }*/

}
