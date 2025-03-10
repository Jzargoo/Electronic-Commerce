package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "shopping_cart")
public class Cart {

    // Relation because in a bargain something like relation between buyer and owner
    @EmbeddedId
    private Relation relation;

    @Column("last_added_at")
    private LocalDate LastUpdate;

    private Float payment;
    private Integer quantity;

}
