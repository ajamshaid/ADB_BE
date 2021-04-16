package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BCA")
@Getter
@Setter
public class BCA {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BCA_UNIQUE_NUMBER", length = 30, nullable = false)
    public String bcaUniqueIdNumber;

    @Column(name = "GD_NUMBER", length = 24)
    public String gdNumber;

    @Column(name = "IBAN", length = 24, nullable = false)
    public String iban;

    @Column(name = "EXPORTER_NTN", length = 9, nullable = false)
    public String exporterNtn;

    @Column(name = "EXPORTER_NAME", length = 100, nullable = false)
    public String exporterName;

    @Column(name = "BCA_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date bcaDate;

    @Column(name = "MODE_OF_PAYMENT", length = 3, nullable = false)
    public String modeOfPayment;

    @Column(name = "FIN_INS_UNIQUE_NUMBER", length = 30, nullable = false)
    public String finInsUniqueNumber;

    @Column(name = "BCA_EVENT_NAME", length = 50, nullable = false)
    public String bcaEventName;

    @Column(name = "EVENT_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date eventDate;

    @Column(name = "RUNNING_SERIAL_NUMBER", length = 20, nullable = false)
    public String runningSerialNumber;

    @Column(name = "SWIFT_REFERENCE", length = 20, nullable = false)
    public String swiftReference;

    @Column(name = "BILL_NUMBER", length = 20, nullable = false)
    public String billNumber;

    @Column(name = "BILL_DATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date billDated;

    @Column(name = "Bill_Amount", precision = 24, scale = 4)
    public BigDecimal billAmount;

    @Column(name = "Invoice_Number", length = 20, nullable = false)
    public String invoiceNumber;

    @Column(name = "Invoice_Date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date invoiceDate;

    @Column(name = "Invoice_Amount", precision = 24, scale = 4)
    public BigDecimal invoiceAmount;

    @Column(name = "Foreign_Bank_Charges_Fcy", precision = 24, scale = 4)
    public BigDecimal foreignBankChargesFcy;

    @Column(name = "Agent_Commission_Fcy", precision = 24, scale = 4)
    public BigDecimal agentCommissionFcy;

    @Column(name = "Withholding_Tax_Pkr", precision = 24, scale = 4)
    public BigDecimal withholdingTaxPkr;

    @Column(name = "EDS_Pkr", precision = 24, scale = 4)
    public BigDecimal edsPkr;

    @Column(name = "BCA_FC", precision = 24, scale = 4)
    public BigDecimal bcaFc;

    @Column(name = "Fcy_Exchange_Rate", precision = 24, scale = 4)
    public BigDecimal fcyExchangeRate;

    @Column(name = "bca_Pkr", precision = 24, scale = 4)
    public BigDecimal bcaPkr;

    @Column(name = "date_Of_Realized", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateOfRealized;

    @Column(name = "adjust_From_Special_Fcy_Acc", precision = 24, scale = 4)
    public BigDecimal adjustFromSpecialFcyAcc;

    @Column(name = "currency", length = 3, nullable = false)
    public String currency;

    @Column(name = "IS_Fin_Ins_Currency_Diff",length = 3, nullable = false)
    private String isFinInsCurrencyDiff;

    @Column(name = "IS_Rem_Amt_Settled_With_Discount",length = 3, nullable = false)
    private String isRemAmtSettledWithDiscount;

    @Column(name = "Amount_Realized", precision = 24, scale = 4)
    public BigDecimal amountRealized;

    @Column(name = "Balance", precision = 24, scale = 4)
    public BigDecimal balance;

    @Column(name = "Allowed_Discount", precision = 24, scale = 4)
    public BigDecimal allowedDiscount;

    @Column(name = "Allowed_Discount_Percentage", precision = 24, scale = 4)
    public BigDecimal allowedDiscountPercentage;

    @Column(name = "REMARKS", length = 300)
    public String remarks;

}

