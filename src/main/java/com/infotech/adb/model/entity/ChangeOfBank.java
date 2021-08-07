package com.infotech.adb.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CHANGE_OF_BANK")
@Data
public class ChangeOfBank {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COB_UNIQUE_NO")
    private String cobUniqueIdNumber;

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

    @Column(name = "COB_STATUS")
    private String cobStatus;

    @Column(name = "LAST_MODIFIED_BY",length = 30, nullable = true)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
}
