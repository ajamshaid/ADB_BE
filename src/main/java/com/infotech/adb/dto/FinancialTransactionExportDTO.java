package com.infotech.adb.dto;

import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class FinancialTransactionExportDTO implements BaseDTO<FinancialTransactionExportDTO, FinancialTransaction> {
    private  Long ftId ;
    private String type;
    private String exporterNtn;
    private String exporterName;
    private String exporterIban;
    private String modeOfPayment;
    private String finInsUniqueNumber;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private CCDataDTO contractCollectionData;
    private LCDataDTO lcData;

    private OpenAccountDataDTO openAccountData;
    private PaymentInformationExportDTO paymentInformation;
    private Set<ItemInformationExportDTO> itemInformation;

    public FinancialTransactionExportDTO(FinancialTransaction entity) {
        convertToDTO(entity, true);
    }

    @Override
    public FinancialTransaction convertToEntity() {
        FinancialTransaction entity = new FinancialTransaction();

        entity.setType(this.getType());
        entity.setId(this.getFtId());
        entity.setNtn(this.getExporterNtn());
        entity.setName(this.getExporterName());
        entity.setIban(this.getExporterIban());

        entity.setModeOfPayment(this.getModeOfPayment());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());

        if(!AppUtility.isEmpty(this.getOpenAccountData()))
            entity.setOpenAcctGDNumber(this.getOpenAccountData().getGdNumber());

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
            for (ItemInformationExportDTO itemDTO : this.getItemInformation()) {
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
            this.setExporterNtn(entity.getNtn());
            this.setExporterName(entity.getName());
            this.setExporterIban(entity.getIban());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setModeOfPayment(entity.getModeOfPayment());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            if(!AppUtility.isEmpty(entity.getOpenAcctGDNumber())){
                FinancialTransactionExportDTO.OpenAccountDataDTO dataDTO = new FinancialTransactionExportDTO.OpenAccountDataDTO();
                dataDTO.setGdNumber(entity.getOpenAcctGDNumber());
                this.setOpenAccountData(dataDTO);
            }

            if (!AppUtility.isEmpty(entity.getPaymentInformation())) {
                this.setPaymentInformation(new PaymentInformationExportDTO(entity.getPaymentInformation()));
            }

            //    if (!AppUtility.isEmpty(entity.getCcData()) && AppConstants.PAYMENT_MODE.IMPORT_CC.equals(entity.getModeOfPayment())) {
            this.setContractCollectionData(new CCDataDTO(entity.getCcData()));
            //   }

            ///     if (!AppUtility.isEmpty(entity.getLcData()) && AppConstants.PAYMENT_MODE.IMPORT_LC.equals(entity.getModeOfPayment())) {
            this.setLcData(new LCDataDTO(entity.getLcData()));
            //     }

            //Item Information
            if (!AppUtility.isEmpty(entity.getItemInformationSet())) {
                HashSet<ItemInformationExportDTO> set = new HashSet<>();
                for (ItemInformation item : entity.getItemInformationSet()) {
                    set.add(new ItemInformationExportDTO(item));
                }
                this.setItemInformation(set);
            }
        }
    }

    @Override
    public FinancialTransactionExportDTO convertToNewDTO(FinancialTransaction entity, boolean partialFill) {
        FinancialTransactionExportDTO dto = new FinancialTransactionExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    @NoArgsConstructor
    private class OpenAccountDataDTO {
        private String gdNumber;
    }
}