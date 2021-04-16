package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class BCAExportDTO implements BaseDTO<BCAExportDTO, BCA> {

    public String bcaUniqueIdNumber;
    public String gdNumber;
    public String iban;
    public String exporterNtn;
    public String exporterName;
    public String bcaDate;
    public String modeOfPayment;
    public String finInsUniqueNumber;
    public BCAInformation bcaInformation;
    public String remarks;
    public Deductions deductions;

    public NetAmountRealized netAmountRealized;

    @Override
    public BCA convertToEntity() {
        BCA entity = new BCA();
        return entity;
    }

    @Override
    public void convertToDTO(BCA entity, boolean partialFill) {
        if (entity != null) {
            this.setGdNumber(entity.getGdNumber());
            this.setBcaUniqueIdNumber(entity.getBcaUniqueIdNumber());
            this.setIban(entity.getIban());
            this.setExporterName(entity.getExporterName());
            this.setExporterNtn(entity.getExporterNtn());
            this.setBcaDate(AppUtility.formatedDate(entity.getBcaDate()));
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
    public BCAExportDTO convertToNewDTO(BCA entity, boolean partialFill) {
        BCAExportDTO dto = new BCAExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    @NoArgsConstructor
    public class NetAmountRealized {
        public BigDecimal bcaFc;
        public BigDecimal fcyExchangeRate;
        public BigDecimal bcaPkr;
        public Date dateOfRealized;
        public BigDecimal adjustFromSpecialFcyAcc;
        public String currency;
        private String isFinInsCurrencyDiff;
        private String isRemAmtSettledWithDiscount;
        public BigDecimal amountRealized;
        public BigDecimal balance;
        public BigDecimal allowedDiscount;
        public BigDecimal allowedDiscountPercentage;

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
        public String eventDate;
        public String runningSerialNumber;
        public String swiftReference;
        public String billNumber;
        public String billDated;
        public BigDecimal billAmount;
        public String invoiceNumber;
        public String invoiceDate;
        public BigDecimal invoiceAmount;

        public BCAInformation(String bcaEventName, Date eventDate, String runningSerialNumber
                , String swiftReference, String billNumber, Date billDated, BigDecimal billAmount
                , String invoiceNumber, Date invoiceDate, BigDecimal invoiceAmount) {
            this.bcaEventName = bcaEventName;
            this.eventDate = AppUtility.formatedDate(eventDate);
            this.runningSerialNumber = runningSerialNumber;
            this.swiftReference = swiftReference;
            this.billNumber = billNumber;
            this.billDated = AppUtility.formatedDate(billDated);
            this.billAmount = billAmount;
            this.invoiceNumber = invoiceNumber;
            this.invoiceDate = AppUtility.formatedDate(invoiceDate);
            this.invoiceAmount = invoiceAmount;
        }
    }
}
