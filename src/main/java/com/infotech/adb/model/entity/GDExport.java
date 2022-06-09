package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "GD_EXPORT")
@Getter
@Setter
public class GDExport {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GD_NUMBER", length = 24, nullable = false)
    private String gdNumber;

    @Column(name = "GD_STATUS", length = 3, nullable = false)
    private String gdStatus;

    @Column(name = "GD_TYPE", length = 30, nullable = false)
    private String gdType;

    @Column(name = "CONSIGNMENT_CATEGORY", length = 50, nullable = false)
    private String consignmentCategory;

    @Column(name = "COLLECTORATE", length = 100, nullable = false)
    private String collectorate;

    @Column(name = "BL_AWB_NUM", length = 50, nullable = true)
    private String blAwbNumber;

    @Column(name = "BL_AWB_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date blAwbDate;

    @Column(name = "VIR_AIR_NUM", length = 50, nullable = true)
    private String virAirNumber;

   /// Consigner and Consignee Information
    @Column(name = "NTN_FTN", length = 9, nullable = false)
    private String ntnFtn;

    @Column(name = "STRN", length = 13, nullable = true)
    private String strn;

    @Column(name = "CONSIGNEE_NAME", length = 100, nullable = false)
    private String consigneeName;

    @Column(name = "CONSIGNEE_ADDRESS", length = 300, nullable = false)
    private String consigneeAddress;

    @Column(name = "CONSIGNOR_NAME", length = 100, nullable = false)
    private String consignorName;

    @Column(name = "CONSIGNOR_ADDRESS", length = 300, nullable = false)
    private String consignorAddress;

    @Column(name = "CURRENCY",length = 3, nullable = true)
    private String currency;

    @Column(name = "TOTAL_DECLARED_VALUE", precision=24, scale=4 ) //RENAME FORM TOTAL_INVOICE_VALUE
    private BigDecimal totalDeclaredValue;

    @Column(name = "INVOICE_Num",length = 30, nullable = true)
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "DELIVERY_TERM",length = 3, nullable = true)
    private String deliveryTerm;

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

    @Column(name = "EXCHANGE_RATE", precision=24, scale=4, nullable = true)
    private BigDecimal exchangeRate;

    @Column(name = "NET_WEIGHT",length = 20, nullable = false)
    private String netWeight;

    @Column(name = "GROSS_WEIGHT",length = 20, nullable = false)
    private String grossWeight;

    @Column(name = "CONSIGNMENT_TYPE", length = 20)
    private String consignmentType;

    @Column(name = "PORT_OF_SHIPMENT", length = 10)
    private String portOfShipment;

    @Column(name = "Place_OF_DELIVERY", length = 100, nullable = false)
    private String placeOfDelivery;

    @Column(name = "PORT_OF_DISCHARGE", length = 10, nullable = false)
    private String portOfDischarge;

    @Column(name = "TERMINAL_LOCATION", length = 70, nullable = false)
    private String terminalLocation;

    @Column(name = "SHIPPING_LINE", length = 100)
    private String shippingLine;

    @Column(name = "NEG_COUNTRY", length = 3)
    private String negativeCountry;

    @Column(name = "NEG_SUPPLIER", length = 300)
    private String negativeSupplier;

    @Column(name = "NEG_COMMODITIES", length = 15000)
    private String negativeCommodities;

    @OneToMany(mappedBy = "gdExport", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<GDPackageInfo> packagesInformationSet;

    @OneToMany(mappedBy = "gdExport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GDContainerVehicleInfo> containerVehicleInformationSet;

    @OneToMany(mappedBy = "gdExport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GDFinancialInstrument> gdFinancialInstrumentSet;

    @OneToMany(mappedBy = "gdExport", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<ItemInformation> itemInformationSet;

    @Column(name = "LAST_MODIFIED_BY",length = 30, nullable = true)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="COB_GD_FT_ID", nullable=true)
    private COBGdFt cobGdFt;
}
