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

    @Column(name = "BENEFICIARY_NAME",length = 100, nullable = true)
    private String beneficiaryName;
    @Column(name = "BENEFICIARY_ADDRESS",length = 300, nullable = true)
    private String beneficiaryAddress;
    @Column(name = "BENEFICIARY_COUNTRY",length = 3, nullable = true)
    private String beneficiaryCountry;
    @Column(name = "BENEFICIARY_IBAN",length = 24, nullable = true)
    private String beneficiaryIban;

    @Column(name = "EXPORTER_NAME",length = 100, nullable = true)
    private String exporterName;
    @Column(name = "EXPORTER_ADDRESS",length = 300, nullable = true)
    private String exporterAddress;
    @Column(name = "EXPORTER_COUNTRY",length = 3, nullable = true)
    private String exporterCountry;

    @Column(name = "PORT_OF_SHIPMENT",length = 10)
    private String portOfShipment;

    @Column(name = "PORT_OF_DISCHARGE",length = 10)
    private String portOfDischarge;


    @Column(name = "DELIVERY_TERM",length = 3, nullable = true)
    private String deliveryTerm;

    @Column(name = "EXCHANGE_RATE", precision=24, scale=4, nullable = true)
    private BigDecimal exchangeRate;

    @Column(name = "LC_CONTRACT_NO",length = 30, nullable = true)
    private String lcContractNo;

    @Column(name = "FIN_INS_VALUE", precision=24, scale=4, nullable = true)
    private BigDecimal financialInstrumentValue;

    @Column(name = "FIN_INS_CURRENCY",length = 3, nullable = true)
    private String financialInstrumentCurrency;

    @Column(name = "TOTAL_VALUE_OF_SHIPMENT", precision=24, scale=4 ) //used in share FinInfo for Export --> Message 5.2.1
    private BigDecimal totalValueOfShipment;

    @Column(name = "TOTAL_DECLARED_VALUE", precision=24, scale=4 ) //RENAME FORM TOTAL_INVOICE_VALUE
    private BigDecimal totalDeclaredValue;

    @Column(name = "INVOICE_Num",length = 30, nullable = true)
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE", nullable = true)
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



    @OneToOne
    @JoinColumn(name="FIN_TRANSACTION_ID", nullable=false)
    private FinancialTransaction financialTransaction;


    @Override
    public String toString() {
        return "PaymentInformation{" +
                "id=" + id +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryAddress='" + beneficiaryAddress + '\'' +
                ", beneficiaryCountry='" + beneficiaryCountry + '\'' +
                ", beneficiaryIban='" + beneficiaryIban + '\'' +
                ", exporterName='" + exporterName + '\'' +
                ", exporterAddress='" + exporterAddress + '\'' +
                ", exporterCountry='" + exporterCountry + '\'' +
                ", portOfShipment='" + portOfShipment + '\'' +
                ", portOfDischarge='" + portOfDischarge + '\'' +
                ", deliveryTerm='" + deliveryTerm + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", lcContractNo='" + lcContractNo + '\'' +
                ", financialInstrumentValue=" + financialInstrumentValue +
                ", financialInstrumentCurrency='" + financialInstrumentCurrency + '\'' +
                ", totalValueOfShipment=" + totalValueOfShipment +
                ", totalDeclaredValue=" + totalDeclaredValue +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", fobValueUsd=" + fobValueUsd +
                ", freightUsd=" + freightUsd +
                ", cfrValueUsd=" + cfrValueUsd +
                ", insuranceUsd=" + insuranceUsd +
                ", landingChargesUsd=" + landingChargesUsd +
                ", assessedValueUsd=" + assessedValueUsd +
                ", otherCharges=" + otherCharges +
                '}';
    }
}
