package com.infotech.adb.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SETTLEMENT_OF_FI")
@Data
public class SettelmentOfFI {
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

    @Column(name = "BDA_BCA_UNIQUE_NO")
    private String bdaBcaUniqueIdNumber;

    @Column(name = "FIN_INS_UNIQUE_NO")
    private String finInsUniqueNumber;

    @Column(name = "FIN_INS_VALUE")
    private String finInsValue;

    @Column(name = "TOTAL_VALUE_OF_SHARED_BCA_BDA")
    private String totalValueOfSharedBCABDA;

    @Column(name = "BALANCE")
    private String balance;
}
