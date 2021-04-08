package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PAYMENT_INFORMATION")
@Getter
@Setter
public class PaymentInformation {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BENEFICIARY_NAME",length = 100, nullable = false)
    private String beneficiaryName;
    @Column(name = "BENEFICIARY_ADDRESS",length = 300, nullable = false)
    private String beneficiaryAddress;
    @Column(name = "BENEFICIARY_COUNTRY",length = 3, nullable = false)
    private String beneficiaryCountry;
    @Column(name = "BENEFICIARY_IBAN",length = 24, nullable = true)
    private String beneficiaryIBAN;

    @Column(name = "EXPORTER_NAME",length = 100, nullable = false)
    private String exporterName;
    @Column(name = "EXPORTER_ADDRESS",length = 300, nullable = false)
    private String exporterAddress;
    @Column(name = "EXPORTER_COUNTRY",length = 3, nullable = false)
    private String exporterCountry;

    @Column(name = "PORT_OF_SHIPMENT",length = 10, nullable = true)
    private String portOfShipment;
    @Column(name = "DELIVERY_TERMS",length = 3, nullable = false)
    private String deliveryTerms;
    @Column(name = "TOTAL_INVOICE_VALUE", precision=24, scale=4, nullable = false)
    private BigDecimal totalInvoiceValue;

    @Column(name = "INVOICE_CURRENCY",length = 3, nullable = false)
    private String invoiceCurrency;

    @Column(name = "INVOICE_Num",length = 30, nullable = false)
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "FOB_VALUE_USD", precision=19, scale=4)
    private BigDecimal fobValueUsd;

      @Column(name = "FREIGHT_USD", precision=19, scale=4)
    private BigDecimal freightUsd;

    @Column(name = "CFR_VALUE_USD", precision=19, scale=4)
    private BigDecimal cfrValueUsd;

    @Column(name = "INSURANCE_USSD", precision=19, scale=4)
    private BigDecimal insuranceUsd;

    @Column(name = "LANDING_CHARGES_USD", precision=19, scale=4)
    private BigDecimal landingChargesUsd;

    @Column(name = "ASSESSED_VALUE_USD", precision=19, scale=4)
    private BigDecimal assessedValueUsd;

    @Column(name = "OTHER_CHARGES", precision=19, scale=4)
    private BigDecimal otherCharges;

    @Column(name = "EXCHANGE_RATE", precision=24, scale=4, nullable = false)
    private BigDecimal exchangeRate;

    @Column(name = "LC_CONTRACT_NO",length = 30, nullable = true)
    private String lcContractNo;

    @OneToOne
    @JoinColumn(name="FIN_TRANSACTION_ID", nullable=false)
    private FinancialTransaction financialTransaction;

}
