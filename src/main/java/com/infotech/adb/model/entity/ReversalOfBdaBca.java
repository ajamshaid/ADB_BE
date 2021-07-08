package com.infotech.adb.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "REVERSAL_OF_BDA_BCA")
@Data
public class ReversalOfBdaBca {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BDA_BCA_UNIQUE_NO")
    private String bcaBdaUniqueIdNumber;

    @Column(name="TRADE_TYPE")
    private String tradeType;

    @Column(name = "TRADER_NTN")
    private String traderNTN;

    @Column(name = "TRADER_NAME")
    private String traderName;

    @Column(name = "IBAN")
    private String Iban;

    @Column(name = "FIN_INS_UNIQUE_NO")
    private String finInsUniqueNumber;

}
