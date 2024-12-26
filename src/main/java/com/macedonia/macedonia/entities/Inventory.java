package com.macedonia.macedonia.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@OneToMany
    @JoinColumn(name="inventory_id") // clothing relation
    @JsonManagedReference
    private List<Clothing>clothingItems;*/

    @Column(name="total_items")
    private int totalItems;

    @Column (name="last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clothing> clothingItems = new ArrayList<>();


    //====Helper Methods ====

    //Add Clothing Item
    public void addClothingItem(Clothing clothing){
        this.clothingItems.add(clothing);
        this.totalItems=this.clothingItems.size(); //update total items
        this.lastUpdated = LocalDateTime.now();
    }

    //Remove a clothing item
    public void removeClothingItem(Clothing clothing){
        this.clothingItems.remove(clothing);
        this.totalItems=this.clothingItems.size();
        this.lastUpdated=LocalDateTime.now();

    }

    // Update last updated timestamp
    public void updateLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }


}
