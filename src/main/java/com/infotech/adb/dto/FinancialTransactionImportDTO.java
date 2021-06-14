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
    private String importerNtn;
    private String importerName;
    private String importerIban;
    private String modeOfPayment;
    private String finInsUniqueNumber;
    private String remarks;

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

        entity.setId(this.getFtId());
        entity.setNtn(this.getImporterNtn());
        entity.setName(this.getImporterName());
        entity.setIban(this.getImporterIban());

        entity.setModeOfPayment(this.getModeOfPayment());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());
        entity.setRemarks(this.getRemarks());

        if (!AppUtility.isEmpty(this.getFinancialTranInformation())) {
            entity.setIntendedPaymentDate(this.getFinancialTranInformation().getIntendedPayDate());
            entity.setTransportDocumentDate(this.getFinancialTranInformation().getTransportDocDate());
            entity.setFinalDateOfShipment(this.getFinancialTranInformation().getFinalDateOfShipment());
            entity.setFinInsExpiryDate(this.getFinancialTranInformation().getExpiryDate());
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
            this.setImporterNtn(entity.getNtn());
            this.setImporterName(entity.getName());
            this.setImporterIban(entity.getIban());
            this.setModeOfPayment(entity.getModeOfPayment());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setRemarks(entity.getRemarks());

            if (AppUtility.isEmpty(this.getFinancialTranInformation())) {
                this.setFinancialTranInformation(new FinTranInformationDTO());
            }

            this.getFinancialTranInformation().setFinalDateOfShipment(entity.getFinalDateOfShipment());
            this.getFinancialTranInformation().setIntendedPayDate(entity.getIntendedPaymentDate());
            this.getFinancialTranInformation().setTransportDocDate(entity.getTransportDocumentDate());
            this.getFinancialTranInformation().setExpiryDate(entity.getFinInsExpiryDate());


            // Payment Information
            if (!AppUtility.isEmpty(entity.getPaymentInformation())) {
                this.setPaymentInformation(new PaymentInformationImportDTO(entity.getPaymentInformation()));
            }

       //     if (AppConstants.PAYMENT_MODE.IMPORT_OPEN_ACCOUNT.equals(entity.getModeOfPayment())) {
                this.setCashMargin(new CashMarginDTO(entity.getCashMarginPercentage()
                        , entity.getCashMarginValue()));
       //     }

        //    if (!AppUtility.isEmpty(entity.getCcData()) && AppConstants.PAYMENT_MODE.IMPORT_CC.equals(entity.getModeOfPayment())) {
                this.setContractCollectionData(new CCDataDTO(entity.getCcData()));
         //   }

       ///     if (!AppUtility.isEmpty(entity.getLcData()) && AppConstants.PAYMENT_MODE.IMPORT_LC.equals(entity.getModeOfPayment())) {
                this.setLcData(new LCDataDTO(entity.getLcData()));
       //     }

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
    private class FinTranInformationDTO {
        private Date intendedPayDate;
        private java.util.Date transportDocDate;
        private Date finalDateOfShipment;
        private Date expiryDate;
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