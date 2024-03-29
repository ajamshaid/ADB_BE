package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "GD")
@Getter
@Setter
public class GD {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GD_NUMBER", length = 100)
    private String gdNumber;

    @Column(name = "GD_STATUS", length = 3)
    private String gdStatus;

    @Column(name = "GD_TYPE", length = 30)
    private String gdType;

    @Column(name = "CONSIGNMENT_CATEGORY", length = 50)
    private String consignmentCategory;

    @Column(name = "COLLECTORATE", length = 100)
    private String collectorate;

    @Column(name = "BL_AWB_NUM", length = 50)
    private String blAwbNumber;

    @Column(name = "BL_AWB_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date blAwbDate;

    @Column(name = "VIR_AIR_NUM", length = 50)
    private String virAirNumber;

    /// Consigner and Consignee Information
    @Column(name = "NTN_FTN", length = 9)
    private String ntnFtn;

    @Column(name = "STRN", length = 13)
    private String strn;

    @Column(name = "CONSIGNEE_NAME", length = 100)
    private String consigneeName;

    @Column(name = "CONSIGNEE_ADDRESS", length = 300)
    private String consigneeAddress;

    @Column(name = "CONSIGNOR_NAME", length = 100)
    private String consignorName;

    @Column(name = "CONSIGNOR_ADDRESS", length = 300)
    private String consignorAddress;


    @Column(name = "NET_WEIGHT",length = 20)
    private String netWeight;

    @Column(name = "GROSS_WEIGHT",length = 20)
    private String grossWeight;

    @Column(name = "PORT_OF_SHIPMENT", length = 10)
    private String portOfShipment;

    @Column(name = "PORT_OF_DELIVERY", length = 10)
    private String portOfDelivery;

    @Column(name = "PORT_OF_DISCHARGE", length = 10)
    private String portOfDischarge;

    @Column(name = "TERMINAL_LOCATION", length = 70)
    private String terminalLocation;

    @Column(name = "CONSIGNMENT_TYPE", length = 20)
    private String consignmentType;

    @Column(name = "SHIPPING_LINE", length = 100)
    private String shippingLine;

    @Column(name = "NEG_COUNTRY", length = 3)
    private String negativeCountry;

    @Column(name = "NEG_SUPPLIER", length = 300)
    private String negativeSupplier;

    @Column(name = "NEG_COMMODITIES", length = 15000)
    private String negativeCommodities;

    @OneToOne( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="FIN_TRANSACTION_ID", nullable=false)
    private FinancialTransaction financialTransaction;

    @OneToMany(mappedBy = "gd", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<GDPackageInfo> packagesInformationSet;

    @OneToMany(mappedBy = "gd", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GDContainerVehicleInfo> containerVehicleInformationSet;

    @Column(name = "LAST_MODIFIED_BY",length = 30, nullable = true)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="COB_GD_FT_ID")
    private COBGdFt cobGdFt;
}
