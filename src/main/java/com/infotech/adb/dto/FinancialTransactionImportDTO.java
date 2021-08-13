package com.infotech.adb.dto;

import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class FinancialTransactionImportDTO implements BaseDTO<FinancialTransactionImportDTO, FinancialTransaction> {
    private  Long ftId;
    private String type;
    private String importerNtn;
    private String importerName;
    private String importerIban;
    private String modeOfPayment;
    private String finInsUniqueNumber;
    private String finInsUniqueNumberCore;
    private String remarks;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private OpenAccountDataDTO openAccountData;

    private CCDataDTO contractCollectionData;
    private LCDataDTO lcData;
    private CashMarginDTO cashMargin;
    private PaymentInformationImportDTO paymentInformation;
    private Set<ItemInformationImportDTO> itemInformation;
    private FinTranInformationDTO financialTranInformation;

    public FinancialTransactionImportDTO(FinancialTransaction entity) {
        convertToDTO(entity, true);
    }

    @Override
    public FinancialTransaction convertToEntity() {
        FinancialTransaction entity = new FinancialTransaction();

        entity.setType(this.getType());
        entity.setId(this.getFtId());
        entity.setNtn(this.getImporterNtn());
        entity.setName(this.getImporterName());
        entity.setIban(this.getImporterIban());

        entity.setModeOfPayment(this.getModeOfPayment());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());
        entity.setFinInsUniqueNumberCore(this.getFinInsUniqueNumberCore());
        entity.setRemarks(this.getRemarks());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());

        if(!AppUtility.isEmpty(this.getOpenAccountData()))
          entity.setOpenAcctGDNumber(this.getOpenAccountData().getGdNumber());

        if (!AppUtility.isEmpty(this.getFinancialTranInformation())) {
            entity.setIntendedPaymentDate(AppUtility.convertDateFromString(this.getFinancialTranInformation().getIntendedPayDate()));
            entity.setTransportDocumentDate(AppUtility.convertDateFromString(this.getFinancialTranInformation().getTransportDocDate()));
            entity.setFinalDateOfShipment(AppUtility.convertDateFromString(this.getFinancialTranInformation().getFinalDateOfShipment()));
            entity.setFinInsExpiryDate(AppUtility.convertDateFromString(this.getFinancialTranInformation().getExpiryDate()));
        }
        if(!AppUtility.isEmpty(this.getCashMargin())) {
            entity.setCashMarginPercentage(this.getCashMargin().getCashMarginPercentage());
            entity.setCashMarginValue(this.getCashMargin().getCashMarginValue());
        }

        //CC Data
        if(!AppUtility.isEmpty(this.getContractCollectionData())) {
            entity.setCcData(this.getContractCollectionData().convertToEntity());
            entity.getCcData().setFinancialTransaction(entity);
        }

        //LC Data
        if(!AppUtility.isEmpty(this.getLcData())) {
            entity.setLcData(this.getLcData().convertToEntity());
            entity.getLcData().setFinancialTransaction(entity);
        }
        //Payment Information
        if(!AppUtility.isEmpty(this.getPaymentInformation())) {
            entity.setPaymentInformation(this.getPaymentInformation().convertToEntity());
            entity.getPaymentInformation().setFinancialTransaction(entity);
        }

        // Item Information
        if (!AppUtility.isEmpty(this.getItemInformation())) {
            HashSet<ItemInformation> set = new HashSet<>();
            for (ItemInformationImportDTO itemDTO : this.getItemInformation()) {
                ItemInformation item = itemDTO.convertToEntity();
                item.setFinancialTransaction(entity);
                set.add(item);
            }
            entity.setItemInformationSet(set);
        }
        return entity;
    }

    @Override
    public void convertToDTO(FinancialTransaction entity, boolean partialFill) {
        if (entity != null) {
            this.setFtId(entity.getId());
            this.setType(entity.getType());
            this.setImporterNtn(entity.getNtn());
            this.setImporterName(entity.getName());
            this.setImporterIban(entity.getIban());
            this.setModeOfPayment(entity.getModeOfPayment());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setFinInsUniqueNumberCore(entity.getFinInsUniqueNumberCore());
            this.setRemarks(entity.getRemarks());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            if(!AppUtility.isEmpty(entity.getOpenAcctGDNumber())){
                OpenAccountDataDTO dataDTO = new OpenAccountDataDTO();
                dataDTO.setGdNumber(entity.getOpenAcctGDNumber());
                this.setOpenAccountData(dataDTO);
            }

            if (AppUtility.isEmpty(this.getFinancialTranInformation())) {
                this.setFinancialTranInformation(new FinTranInformationDTO());
            }

            this.getFinancialTranInformation().setFinalDateOfShipment(AppUtility.formatedDate(entity.getFinalDateOfShipment()));
            this.getFinancialTranInformation().setIntendedPayDate(AppUtility.formatedDate(entity.getIntendedPaymentDate()));
            this.getFinancialTranInformation().setTransportDocDate(AppUtility.formatedDate(entity.getTransportDocumentDate()));
            this.getFinancialTranInformation().setExpiryDate(AppUtility.formatedDate(entity.getFinInsExpiryDate()));


            // Payment Information
            if (!AppUtility.isEmpty(entity.getPaymentInformation())) {
                this.setPaymentInformation(new PaymentInformationImportDTO(entity.getPaymentInformation()));
            }

     //      if (AppConstants.PAYMENT_MODE.IMPORT_OPEN_ACCOUNT.equals(entity.getModeOfPayment())) {
                this.setCashMargin(new CashMarginDTO(entity.getCashMarginPercentage()
                        , entity.getCashMarginValue()));
     //       }

      //      if (!AppUtility.isEmpty(entity.getCcData()) && AppConstants.PAYMENT_MODE.IMPORT_CC.equals(entity.getModeOfPayment())) {
                this.setContractCollectionData(new CCDataDTO(entity.getCcData()));
       //     }

         //   if (!AppUtility.isEmpty(entity.getLcData()) && AppConstants.PAYMENT_MODE.LC_VALUE.equals(entity.getModeOfPayment())) {
                this.setLcData(new LCDataDTO(entity.getLcData()));
          ///  }

            if (!AppUtility.isEmpty(entity.getItemInformationSet())) {
                HashSet<ItemInformationImportDTO> set = new HashSet<>();
                for (ItemInformation item : entity.getItemInformationSet()) {
                    set.add(new ItemInformationImportDTO(item));
                }
                this.setItemInformation(set);
            }
        }
    }

    @Override
    public FinancialTransactionImportDTO convertToNewDTO(FinancialTransaction entity, boolean partialFill) {
        FinancialTransactionImportDTO dto = new FinancialTransactionImportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    @NoArgsConstructor
    private class OpenAccountDataDTO {
        private String gdNumber;
    }

    @Data
    @NoArgsConstructor
    private class FinTranInformationDTO {
        private String intendedPayDate;
        private String transportDocDate;
        private String finalDateOfShipment;
        private String expiryDate;
    }

    @Data
    @NoArgsConstructor
    private class CashMarginDTO {
        private BigDecimal cashMarginPercentage;
        private BigDecimal cashMarginValue;

        public CashMarginDTO(BigDecimal cashMarginPercentage, BigDecimal cashMarginValue) {
            this.cashMarginPercentage = cashMarginPercentage;
            this.cashMarginValue = cashMarginValue;
        }
    }
}