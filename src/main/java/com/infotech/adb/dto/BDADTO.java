package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BDA;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BDADTO implements BaseDTO<BDADTO, BDA> {
    private Long id;
    public String bdaUniqueIdNumber;
    public String[] gdNumber ={};
    public String iban;
    public String importerNtn;
    public String importerName;
    public String bdaDate;
    public String modeOfPayment;
    public String finInsUniqueNumber;
    public BdaInformation bdaInformation;

    public BDADTO(BDA entity) {
        convertToDTO(entity, true);
    }

    @Override
    public BDA convertToEntity() {
        BDA entity = new BDA();

        entity.setId(this.getId());
        //entity.setGdNumber(this.getGdNumber());
        entity.setGdNumber(this.getGdNumber()[0]);
        entity.setBdaUniqueIdNumber(this.getBdaUniqueIdNumber());
        entity.setIban(this.getIban());
        entity.setImporterNtn(this.getImporterNtn());
        entity.setImporterName(this.getImporterName());
        entity.setBdaDate(AppUtility.convertDateFromString(this.getBdaDate()));
        entity.setModeOfPayment(this.getModeOfPayment());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());

        if(!AppUtility.isEmpty(this.getBdaInformation())) {
            entity.setTotalBdaAmountFcy(this.getBdaInformation().getTotalBdaAmountFcy());
            entity.setTotalBdaAmountCurrency(this.getBdaInformation().getTotalBdaAmountCurrency());
            entity.setSampleAmountExclude(this.getBdaInformation().getSampleAmountExclude());
            entity.setSampleAmountCurrency(this.getBdaInformation().getSampleAmountCurrency());
            entity.setNetBdaAmountFcy(this.getBdaInformation().getNetBdaAmountFcy());
            entity.setNetBdaAmountCurrency(this.getBdaInformation().getNetBdaAmountCurrency());
            entity.setExchangeRateFiFcy(this.getBdaInformation().getExchangeRateFiFcy());
            entity.setNetBdaAmountPkr(this.getBdaInformation().getNetBdaAmountPkr());
            entity.setAmountInWords(this.getBdaInformation().getAmountInWords());
            entity.setCurrencyFcy(this.getBdaInformation().getCurrencyFcy());
            entity.setBdaAmountFcy(this.getBdaInformation().getBdaAmountFcy());
            entity.setBdaAmountPkr(this.getBdaInformation().getBdaAmountPkr());

            entity.setBdaDocumentRefNumber(this.getBdaInformation().getBdaDocumentRefNumber());
            entity.setCommissionAmountFcy(this.getBdaInformation().getCommisionAmountFcy());
            entity.setCommissionAmountPkr(this.getBdaInformation().getCommisionAmountPkr());
            entity.setFedFcy(this.getBdaInformation().getFedFcy());
            entity.setFedAmountPkr(this.getBdaInformation().getFedAmountPkr());
            entity.setSwiftChargesPkr(this.getBdaInformation().getSwiftChargesPkr());
            entity.setOtherChargesPkr(this.getBdaInformation().getOtherChargesPkr());
            entity.setRemarks(this.getBdaInformation().getRemarks());
            entity.setBalanceBdaAmountFcy(this.getBdaInformation().getBalanceBdaAmountFcy());

            entity.setExchangeRateFcy(this.getBdaInformation().getExchangeRateFcy());
        }
        return entity;
    }

    @Override
    public void convertToDTO(BDA entity, boolean partialFill) {
        if (entity != null) {
            this.setId(entity.getId());
            String[] gdNum = {entity.getGdNumber()};
            this.setGdNumber(gdNum);
            //this.setGdNumber(entity.getGdNumber());
            this.setBdaUniqueIdNumber(entity.getBdaUniqueIdNumber());
            this.setIban(entity.getIban());
            this.setImporterNtn(entity.getImporterNtn());
            this.setImporterName(entity.getImporterName());
            this.setBdaDate(AppUtility.formatedDate(entity.getBdaDate()));
            this.setModeOfPayment(entity.getModeOfPayment());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setBdaInformation(new BdaInformation(entity.getTotalBdaAmountFcy(),entity.getTotalBdaAmountCurrency()
                    ,entity.getSampleAmountExclude(),entity.getSampleAmountCurrency()
                    ,entity.getNetBdaAmountFcy(),entity.getNetBdaAmountCurrency()
                    ,entity.getExchangeRateFiFcy(),entity.getNetBdaAmountPkr()
                    ,entity.getAmountInWords(), entity.getCurrencyFcy(), entity.getBdaAmountFcy()
                    ,entity.getBdaAmountPkr(),entity.getBdaDocumentRefNumber(), entity.getCommissionAmountFcy()
                    ,entity.getCommissionAmountPkr(), entity.getFedFcy(), entity.getFedAmountPkr()
                    ,entity.getSwiftChargesPkr(),entity.getOtherChargesPkr(),entity.getRemarks()
                    ,entity.getBalanceBdaAmountFcy() ,entity.getExchangeRateFcy()
            ));
        }
    }

    @Override
    public BDADTO convertToNewDTO(BDA entity, boolean partialFill) {
        BDADTO dto = new BDADTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    @NoArgsConstructor
    public class BdaInformation {
        public BigDecimal totalBdaAmountFcy;
        public String totalBdaAmountCurrency;
        public BigDecimal sampleAmountExclude;
        public String sampleAmountCurrency;
        public BigDecimal netBdaAmountFcy;
        public String netBdaAmountCurrency;
        public BigDecimal exchangeRateFiFcy;
        public BigDecimal netBdaAmountPkr;
        public String amountInWords;
        public String currencyFcy;
        public BigDecimal bdaAmountFcy;
        public BigDecimal bdaAmountPkr;
        public BigDecimal exchangeRateFcy;
        public String bdaDocumentRefNumber;
        public BigDecimal commisionAmountFcy;
        public BigDecimal commisionAmountPkr;
        public BigDecimal fedFcy;
        public BigDecimal fedAmountPkr;
        public BigDecimal swiftChargesPkr;
        public BigDecimal otherChargesPkr;
        public String remarks;
        public BigDecimal balanceBdaAmountFcy;

        public BdaInformation(BigDecimal totalBdaAmountFcy, String totalBdaAmountCurrency
                , BigDecimal sampleAmountExclude, String sampleAmountCurrency
                , BigDecimal netBdaAmountFcy, String netBdaAmountCurrency
                , BigDecimal exchangeRateFiFcy, BigDecimal netBdaAmountPkr
                , String amountInWords, String currencyFcy, BigDecimal bdaAmountFcy
                , BigDecimal bdaAmountPkr, String bdaDocumentRefNumber, BigDecimal commisionAmountFcy
                , BigDecimal commisionAmountPkr, BigDecimal fedFcy, BigDecimal fedAmountPkr
                , BigDecimal swiftChargesPkr, BigDecimal otherChargesPkr, String remarks
                , BigDecimal balanceBdaAmountFcy, BigDecimal exchangeRateFcy) {
            this.totalBdaAmountFcy = totalBdaAmountFcy;
            this.totalBdaAmountCurrency = totalBdaAmountCurrency;
            this.sampleAmountExclude = sampleAmountExclude;
            this.sampleAmountCurrency = sampleAmountCurrency;
            this.netBdaAmountFcy = netBdaAmountFcy;
            this.netBdaAmountCurrency = netBdaAmountCurrency;
            this.exchangeRateFiFcy = exchangeRateFiFcy;
            this.netBdaAmountPkr = netBdaAmountPkr;
            this.amountInWords = amountInWords;
            this.currencyFcy = currencyFcy;
            this.bdaAmountFcy = bdaAmountFcy;
            this.bdaAmountPkr = bdaAmountPkr;
            this.bdaDocumentRefNumber = bdaDocumentRefNumber;
            this.commisionAmountFcy = commisionAmountFcy;
            this.commisionAmountPkr = commisionAmountPkr;
            this.fedFcy = fedFcy;
            this.fedAmountPkr = fedAmountPkr;
            this.swiftChargesPkr = swiftChargesPkr;
            this.otherChargesPkr = otherChargesPkr;
            this.remarks = remarks;
            this.balanceBdaAmountFcy = balanceBdaAmountFcy;
            this.exchangeRateFcy = exchangeRateFcy;
        }
    }

}
