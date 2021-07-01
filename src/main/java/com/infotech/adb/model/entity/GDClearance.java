package com.infotech.adb.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "GD_CLEARANCE")
@Data
public class GDClearance {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="TRADE_TYPE")
    private String tradeType;

    @Column(name = "TRADER_NTN")
    private String traderNTN;

    @Column(name = "TRADER_NAME")
    private String traderName;

    @Column(name = "IBAN")
    private String Iban;

    @Column(name = "GD_NUMBER")
    private String gdNumber;

    @Column(name = "CLEARANCE_STATUS")
    private String clearanceStatus;
}
