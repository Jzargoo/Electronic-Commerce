package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class SellerInfo {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "seller_id")
    private User seller;


    private String companyName;
    private boolean approved;
    @OneToOne
    @JoinColumn(name = "bank_details")
    private BankDetails bankDetails;
}
