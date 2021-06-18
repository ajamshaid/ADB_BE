package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "LC_DATA")
@Getter
@Setter
public class LCData {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADV_PAY_PERCENTAGE", precision=7, scale=4, nullable = true)
    private BigDecimal advPayPercentage;

    @Column(name = "SIGHT_PERCENTAGE",precision=7, scale=4, nullable = true)
    private BigDecimal sightPercentage;

    @Column(name = "USANCE_PERCENTAGE",precision=7, scale=4, nullable = true)
    private BigDecimal usancePercentage;

    @Column(name = "DAYS",length = 3, nullable = true)
    private Integer days;

    @Column(name = "TOTAL_PERCENTAGE",precision=7, scale=4, nullable = true)
    private BigDecimal totalPercentage;

    /*
     * Entity Specific Fields
     */
    @OneToOne
    @JoinColumn(name="FIN_TRANSACTION_ID", nullable=false)
    private FinancialTransaction financialTransaction;

    @Override
    public String toString() {
        return "LCData{" +
                "id=" + id +
                ", advPayPercentage=" + advPayPercentage +
                ", sightPercentage=" + sightPercentage +
                ", usancePercentage=" + usancePercentage +
                ", days=" + days +
                ", totalPercentage=" + totalPercentage +
                '}';
    }
}
