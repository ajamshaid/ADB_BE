package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CC_DATA")
@Getter
@Setter
public class CCData{
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADV_PAY_PERCENTAGE", precision=7, scale=4, nullable = true)
    private BigDecimal advPayPercentage;

    @Column(name = "DOC_AGAINST_PAY_PERCENTAGE",precision=7, scale=4, nullable = true)
    private BigDecimal docAgainstPayPercentage;

    @Column(name = "DOC_AGAINST_ACCEPTANCE_PERCENTAGE",precision=7, scale=4, nullable = true)
    private BigDecimal docAgainstAcceptancePercentage;

    @Column(name = "DAYS",length = 3, nullable = true)
    private Integer days;

    @Column(name = "TOTAL_PERCENTAGE",precision=7, scale=4, nullable = false)
    private BigDecimal totalPercentage;

    /*
     * Entity Specific Fields
     */
    @OneToOne
    @JoinColumn(name="FIN_TRANSACTION_ID", nullable=false)
    private FinancialTransaction financialTransaction;

}
