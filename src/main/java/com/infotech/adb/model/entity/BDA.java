package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BDA")
@Getter
@Setter
@ToString
public class BDA {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BDA_UNIQUE_NUMBER", length = 30, nullable = true)
    public String bdaUniqueIdNumber;

    @Column(name = "GD_NUMBER", length = 24)
    public String gdNumber;

    @Column(name = "IBAN", length = 24, nullable = true)
    public String iban;

    @Column(name = "IMPORTER_NTN", length = 9, nullable = true)
    public String importerNtn;

    @Column(name = "IMPORTER_NAME", length = 100, nullable = true)
    public String importerName;

    @Column(name = "BDA_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date bdaDate;

    @Column(name = "MODE_OF_PAYMENT", length = 3, nullable = true)
    public String modeOfPayment;

    @Column(name = "FIN_INS_UNIQUE_NUMBER", length = 30, nullable = true)
    public String finInsUniqueNumber;

    @Column(name = "TOTAL_BDA_AMOUNT_FCY", precision = 24, scale = 4)
    public BigDecimal totalBdaAmountFcy;

    @Column(name = "TOTAL_BDA_AMOUNT_CURRENCY", length = 3)
    public String totalBdaAmountCurrency;

    @Column(name = "SAMPLE_AMOUNT_EXCLUDE", precision = 24, scale = 4)
    public BigDecimal sampleAmountExclude;

    @Column(name = "SAMPLE_AMOUNT_CURRENCY", length = 3)
    public String sampleAmountCurrency;

    @Column(name = "NET_BDA_AMOUNT_FCY", precision = 24, scale = 4)
    public BigDecimal netBdaAmountFcy;

    @Column(name = "NET_BDA_AMOUNT_CURRENCY", length = 3, nullable = true)
    public String netBdaAmountCurrency;

    @Column(name = "EXCHANGE_RATE_FI_FCY", precision = 24, scale = 4)
    public BigDecimal exchangeRateFiFcy;

    @Column(name = "EXCHANGE_RATE_FCY", precision = 24, scale = 4)
    public BigDecimal exchangeRateFcy;

    @Column(name = "NET_BDA_AMOUNT_PKR", precision = 24, scale = 4)
    public BigDecimal netBdaAmountPkr;

    @Column(name = "AMOUNT_IN_WORDS", length = 300, nullable = true)
    public String amountInWords;

    @Column(name = "CURRENCY_FCY", length = 3, nullable = true)
    public String currencyFcy;

    @Column(name = "BDA_AMOUNT_FCY", precision = 24, scale = 4)
    public BigDecimal bdaAmountFcy;

    @Column(name = "BDA_AMOUNT_PKR", precision = 24, scale = 4)
    public BigDecimal bdaAmountPkr;


    @Column(name = "BDA_DOC_REF_NUMBER", length = 300, nullable = true)
    public String bdaDocumentRefNumber;

    @Column(name = "COMMISSION_AMOUNT_FCY", precision = 24, scale = 4)
    public BigDecimal commissionAmountFcy;

    @Column(name = "COMMISSION_AMOUNT_PKR", precision = 24, scale = 4)
    public BigDecimal commissionAmountPkr;

    @Column(name = "FED_FCY", precision = 24, scale = 4)
    public BigDecimal fedFcy;

    @Column(name = "FED_AMOUNT_PKR", precision = 24, scale = 4)
    public BigDecimal fedAmountPkr;

    @Column(name = "SWIFT_CHARGES_PKR", precision = 24, scale = 4)
    public BigDecimal swiftChargesPkr;

    @Column(name = "OTHER_CHARGES_PKR", precision = 24, scale = 4)
    public BigDecimal otherChargesPkr;

    @Column(name = "REMARKS", length = 300)
    public String remarks;

    @Column(name = "BALANCE_BDA_AMOUNT_FCY", precision = 24, scale = 4)
    public BigDecimal balanceBdaAmountFcy;
}

