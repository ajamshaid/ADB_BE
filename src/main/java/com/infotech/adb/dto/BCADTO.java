package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class BCADTO implements BaseDTO<BCADTO, BCA> {

    private Long id;
    public String bcaUniqueIdNumber;
    public String gdNumber;
    public String exporterNtn;
    public String exporterName;
    public String iban;
    public String modeOfPayment;
    public String finInsUniqueNumber;

    public Date bcaDate;
    public String remarks;

    public BCAInformation bcaInformation;
    public Deductions deductions;

    public NetAmountRealized netAmountRealized;

    @Override
    public BCA convertToEntity() {
        BCA entity = new BCA();

        entity.setId(this.getId());
        entity.setGdNumber(this.getGdNumber());
        entity.setBcaUniqueIdNumber(this.getBcaUniqueIdNumber());
        entity.setIban(this.getIban());
        entity.setExporterName(this.getExporterName());
        entity.setExporterNtn(this.getExporterNtn());
        entity.setBcaDate(this.getBcaDate());
        entity.setModeOfPayment(this.getModeOfPayment());
        entity.setRemarks(this.getRemarks());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());

        if(!AppUtility.isEmpty(this.getBcaInformation())) {
            entity.setBcaEventName(this.getBcaInformation().getBcaEventName());
            entity.setEventDate(this.getBcaInformation().getEventDate());
            entity.setRunningSerialNumber(this.getBcaInformation().getRunningSerialNumber());
            entity.setSwiftReference(this.getBcaInformation().getSwiftReference());
            entity.setBillNumber(this.getBcaInformation().getBillNumber());
            entity.setBillDated(this.getBcaInformation().getBillDated());
            entity.setBillAmount(this.getBcaInformation().getBillAmount());
            entity.setInvoiceNumber(this.getBcaInformation().getInvoiceNumber());
            entity.setInvoiceDate(this.getBcaInformation().getInvoiceDate());
            entity.setInvoiceAmount(this.getBcaInformation().getInvoiceAmount());
        }

        if(!AppUtility.isEmpty(this.getDeductions())) {
            entity.setForeignBankChargesFcy(this.getDeductions().getForeignBankChargesFcy());
            entity.setAgentCommissionFcy(this.getDeductions().getAgentCommissionFcy());
            entity.setWithholdingTaxPkr(this.getDeductions().getWithholdingTaxPkr());
            entity.setEdsPkr(this.getDeductions().getEdsPkr());
        }
        if(!AppUtility.isEmpty(this.getNetAmountRealized())) {
            entity.setBcaFc(this.getNetAmountRealized().getBcaFc());
            entity.setFcyExchangeRate(this.getNetAmountRealized().getFcyExchangeRate());
            entity.setBcaPkr(this.getNetAmountRealized().getBcaPkr());
            entity.setDateOfRealized(this.getNetAmountRealized().getDateOfRealized());
            entity.setAdjustFromSpecialFcyAcc(this.getNetAmountRealized().getAdjustFromSpecialFcyAcc());
            entity.setCurrency(this.getNetAmountRealized().getCurrency());
            entity.setIsFinInsCurrencyDiff(this.getNetAmountRealized().getIsFinInsCurrencyDiff());
            entity.setIsRemAmtSettledWithDiscount(this.getNetAmountRealized().getIsRemAmtSettledWithDiscount());
            entity.setAmountRealized(this.getNetAmountRealized().getAmountRealized());
            entity.setBalance(this.getNetAmountRealized().getBalance());
            entity.setAllowedDiscount(this.getNetAmountRealized().getAllowedDiscount());
            entity.setAllowedDiscountPercentage(this.getNetAmountRealized().getAllowedDiscountPercentage());
        }
        return entity;
    }

    @Override
    public void convertToDTO(BCA entity, boolean partialFill) {
        if (entity != null) {
            this.setId(entity.getId());
            this.setGdNumber(entity.getGdNumber());
            this.setBcaUniqueIdNumber(entity.getBcaUniqueIdNumber());
            this.setIban(entity.getIban());
            this.setExporterName(entity.getExporterName());
            this.setExporterNtn(entity.getExporterNtn());
            this.setBcaDate(entity.getBcaDate());
            this.setModeOfPayment(entity.getModeOfPayment());
            this.setRemarks(entity.getRemarks());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());

            this.setBcaInformation(new BCAInformation(entity.getBcaEventName(), entity.getEventDate()
                    , entity.getRunningSerialNumber(), entity.getSwiftReference()
                    , entity.getBillNumber(), entity.getBillDated()
                    , entity.getBillAmount(), entity.getInvoiceNumber()
                    , entity.getInvoiceDate(), entity.getInvoiceAmount()));

            this.setDeductions(new Deductions(entity.getForeignBankChargesFcy(), entity.getAgentCommissionFcy()
                    , entity.getWithholdingTaxPkr(), entity.getEdsPkr()));

            this.setNetAmountRealized(new NetAmountRealized(entity.getBcaFc(), entity.getFcyExchangeRate(), entity.getBcaPkr()
                    , entity.getDateOfRealized(), entity.getAdjustFromSpecialFcyAcc(), entity.getCurrency()
                    , entity.getIsFinInsCurrencyDiff(), entity.getIsRemAmtSettledWithDiscount()
                    , entity.getAmountRealized(), entity.getBalance()
                    , entity.getAllowedDiscount(), entity.getAllowedDiscountPercentage()));
        }

    }

    @Override
    public BCADTO convertToNewDTO(BCA entity, boolean partialFill) {
        BCADTO dto = new BCADTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    @NoArgsConstructor
    public class NetAmountRealized {
        private String isFinInsCurrencyDiff;
        public String currency;

        public BigDecimal bcaFc;
        public BigDecimal fcyExchangeRate;
        public BigDecimal bcaPkr;
        public Date dateOfRealized;
        public BigDecimal adjustFromSpecialFcyAcc;


        private String isRemAmtSettledWithDiscount;
        public BigDecimal allowedDiscount;
        public BigDecimal allowedDiscountPercentage;
        public BigDecimal totalBcaAmount;
        public BigDecimal amountRealized;
        public BigDecimal balance;


        public NetAmountRealized(BigDecimal bcaFc, BigDecimal fcyExchangeRate, BigDecimal bcaPkr, Date dateOfRealized, BigDecimal adjustFromSpecialFcyAcc, String currency, String isFinInsCurrencyDiff, String isRemAmtSettledWithDiscount, BigDecimal amountRealized, BigDecimal balance, BigDecimal allowedDiscount, BigDecimal allowedDiscountPercentage) {
            this.bcaFc = bcaFc;
            this.fcyExchangeRate = fcyExchangeRate;
            this.bcaPkr = bcaPkr;
            this.dateOfRealized = dateOfRealized;
            this.adjustFromSpecialFcyAcc = adjustFromSpecialFcyAcc;
            this.currency = currency;
            this.isFinInsCurrencyDiff = isFinInsCurrencyDiff;
            this.isRemAmtSettledWithDiscount = isRemAmtSettledWithDiscount;
            this.amountRealized = amountRealized;
            this.balance = balance;
            this.allowedDiscount = allowedDiscount;
            this.allowedDiscountPercentage = allowedDiscountPercentage;
        }
    }

    @Data
    @NoArgsConstructor
    public class Deductions {
        public BigDecimal foreignBankChargesFcy;
        public BigDecimal agentCommissionFcy;
        public BigDecimal withholdingTaxPkr;
        public BigDecimal edsPkr;

        public Deductions(BigDecimal foreignBankChargesFcy, BigDecimal agentCommissionFcy
                , BigDecimal withholdingTaxPkr, BigDecimal edsPkr) {
            this.foreignBankChargesFcy = foreignBankChargesFcy;
            this.agentCommissionFcy = agentCommissionFcy;
            this.withholdingTaxPkr = withholdingTaxPkr;
            this.edsPkr = edsPkr;
        }
    }
    @Data
    @NoArgsConstructor
    public class BCAInformation {

        public String bcaEventName;
        public Date eventDate;
        public String runningSerialNumber;
        public String swiftReference;
        public String billNumber;
        public Date billDated;
        public BigDecimal billAmount;
        public String invoiceNumber;
        public Date invoiceDate;
        public BigDecimal invoiceAmount;

        public BCAInformation(String bcaEventName, Date eventDate, String runningSerialNumber
                , String swiftReference, String billNumber, Date billDated, BigDecimal billAmount
                , String invoiceNumber, Date invoiceDate, BigDecimal invoiceAmount) {
            this.bcaEventName = bcaEventName;
            this.eventDate = eventDate ;//AppUtility.formatedDate(eventDate);
            this.runningSerialNumber = runningSerialNumber;
            this.swiftReference = swiftReference;
            this.billNumber = billNumber;
            this.billDated = billDated;//AppUtility.formatedDate(billDated);
            this.billAmount = billAmount;
            this.invoiceNumber = invoiceNumber;
            this.invoiceDate = invoiceDate;//AppUtility.formatedDate(invoiceDate);
            this.invoiceAmount = invoiceAmount;
        }
    }
}