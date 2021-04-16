package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "FINANCIAL_TRANSACTION")
@Getter
@Setter
public class FinancialTransaction {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NTN",length = 9, nullable = false)
    private String ntn;
    @Column(name = "NAME",length = 100, nullable = false)
    private String name;

    @Column(name = "PK_REMITTANCE",length = 3, nullable = false)
    private String isPKRemittance;

    @Column(name = "MODE_OF_PAYMENT",length = 3, nullable = false)
    private String modeOfPayment;

    @Column(name = "FIN_INS_UNIQ_NUM",length = 30, nullable = false)
    private String finInsUniqueNumber;


    // Financial Trans Information....
    @Column(name = "INTENDED_PAYMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date intendedPaymentDate;

    @Column(name = "TRANSPORT_DOCUMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transportDocumentDate;

    @Column(name = "FINAL_DATE_OF_SHIPMENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalDateOfShipment;

    @Column(name = "REMARKS",length = 200, nullable = true)
    private String Remarks;

    @Column(name = "ADV_PAY_PERCENTAGE", precision=7, scale=4, nullable = true)
    private BigDecimal advPayPercentage;

    @Column(name = "OPEN_ACC_PERCENTAGE",precision=7, scale=4, nullable = true)
    private BigDecimal openAccPercentage;

    // LC and CC Data
    @OneToOne(mappedBy = "financialTransaction", fetch = FetchType.LAZY)
    private CCData ccData;

    @OneToOne(mappedBy = "financialTransaction", fetch = FetchType.LAZY)
    private LCData lcData;

    @OneToOne(mappedBy = "financialTransaction", fetch = FetchType.LAZY)
    private PaymentInformation paymentInformation;

    @OneToMany(mappedBy = "financialTransaction", fetch = FetchType.LAZY)
    private Set<ItemInformation> itemInformationSet;

    @OneToMany(mappedBy = "financialTransaction", fetch = FetchType.LAZY)
    private Set<FinTransMOP> finTransMOPSet;


}
