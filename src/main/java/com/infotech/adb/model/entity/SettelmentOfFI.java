package com.infotech.adb.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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

    @Column(name = "FIN_INS_VALUE",precision=20, scale=5)
    private BigDecimal finInsValue;

    @Column(name = "TOTAL_VALUE_OF_SHARED_BCA_BDA" ,precision=20, scale=5)
    private BigDecimal totalValueOfSharedBCABDA;

    @Column(name = "BALANCE",precision=20, scale=5)
    private BigDecimal balance;

    @Column(name = "LAST_MODIFIED_BY",length = 30, nullable = true)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "STATUS",length = 20, nullable = false)
    private String status;
}
