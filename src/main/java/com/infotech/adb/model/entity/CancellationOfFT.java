package com.infotech.adb.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CANCELLATION_OF_FT")
@Data
public class CancellationOfFT {
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

    @Column(name = "FIN_INS_UNIQUE_NO")
    private String finInsUniqueNumber;

    @Column(name = "LAST_MODIFIED_BY",length = 30, nullable = true)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "STATUS",length = 20, nullable = false)
    private String status;

}
