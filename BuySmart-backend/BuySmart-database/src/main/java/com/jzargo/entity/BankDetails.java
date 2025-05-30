package com.jzargo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
@Entity
public class BankDetails {
    @Id
    private Long id;
    @MapsId
    @OneToOne
    private SellerInfo info;

    private String bankName;
    private String bankAccount;
    private String bik;
    private String iinOrBin;
    private String legalName;

}
