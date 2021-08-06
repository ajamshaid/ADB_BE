package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "GD_Financial_Instrument")
@Getter
@Setter
@ToString
public class GDFinancialInstrument {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "IBAN",length = 24, nullable = true)
    private String iban;

    @Column(name = "MODE_OF_PAYMENT",length = 30, nullable = true)
    private String modeOfPayment;

    @Column(name = "FIN_INS_UNIQ_NUM",length = 30, nullable = true)
    private String finInsUniqueNumber;

    @Column(name = "FIN_INS_CONSUMED_VALUE", precision=24, scale=4 )
    private BigDecimal finInsConsumedValue;
    /*
     * Entity Specific Fields
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="GD_EXPORT_ID", nullable=true)
    private GDExport gdExport;
}
