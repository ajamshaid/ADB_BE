package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "FINANCIAL_TRANSACTION")
@Getter
@Setter
@ToString
public class FinancialTransaction {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE",length = 10, nullable = true)
    private String type;
    
    @Column(name = "NTN",length = 9, nullable = true)
    private String ntn;

    @Column(name = "NAME",length = 100, nullable = true)
    private String name;

    @Column(name = "IBAN",length = 24, nullable = true)
    private String iban;

    @Column(name = "MODE_OF_PAYMENT",length = 30, nullable = true)
    private String modeOfPayment;

    @Column(name = "FIN_INS_UNIQ_NUM",length = 30, nullable = true)
    private String finInsUniqueNumber;

    @Column(name = "FIN_INS_UNIQ_NUM_CORE",length = 30, nullable = true)
    private String finInsUniqueNumberCore;

    @Column(name = "LAST_MODIFIED_BY",length = 30, nullable = true)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;


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

    @Column(name = "FIN_INS_EXPIRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finInsExpiryDate;

    @Column(name = "REMARKS",length = 500, nullable = true)
    private String Remarks;

//    @Column(name = "ADV_PAY_PERCENTAGE", precision=7, scale=4, nullable = true)
//    private BigDecimal advPayPercentage;
//
//    @Column(name = "OPEN_ACC_PERCENTAGE",precision=7, scale=4, nullable = true)
//    private BigDecimal openAccPercentage;

    //for Payment mode -> Open Account (Cash Margin)

    @Column(name = "CASH_MARGIN_PERCENTAGE",precision=7, scale=4)
    private BigDecimal cashMarginPercentage;

    @Column(name = "CASH_MARGIN_VALUE", precision=19, scale=4)
    private BigDecimal cashMarginValue;

    // LC and CC Data
    @OneToOne(mappedBy = "financialTransaction", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval=true)
    private CCData ccData;

    @OneToOne(mappedBy = "financialTransaction", fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval=true)
    private LCData lcData;

    @OneToOne(mappedBy = "financialTransaction", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private PaymentInformation paymentInformation;

    @OneToMany(mappedBy = "financialTransaction", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<ItemInformation> itemInformationSet;
}
