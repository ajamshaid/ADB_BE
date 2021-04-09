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
public class FinancialTransactionDTO implements BaseDTO<FinancialTransactionDTO, FinancialTransaction> {
    private String importerNtn;
    private String importerName;
    private String remittanceFromPak;
    private String modeOfPayment;
    private String finInsUniqueNumber;
    private String remarks;

    private CCDataDTO contractCollectionData;
    private LCDataDTO lcData;
    private PaymentInformationDTO paymentInformation;
    private Set<ItemInformationDTO> itemInformation;
    private FinTranInformationDTO financialTranInformation;

    public FinancialTransactionDTO(FinancialTransaction entity) {
        convertToDTO(entity, true);
    }

    @Override
    public FinancialTransaction convertToEntity() {
        FinancialTransaction entity = new FinancialTransaction();
        return entity;
    }

    @Override
    public void convertToDTO(FinancialTransaction entity, boolean partialFill) {
        if (entity != null) {
            this.setImporterNtn(entity.getNtn());
            this.setImporterName(entity.getName());
            this.setRemittanceFromPak(entity.getIsPKRemittance());
            this.setModeOfPayment(entity.getModeOfPayment());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setRemarks(entity.getRemarks());

            if (AppUtility.isEmpty(this.getFinancialTranInformation())) {
                this.setFinancialTranInformation(new FinTranInformationDTO());
            }

            this.getFinancialTranInformation().setFinalDateOfShipment(entity.getFinalDateOfShipment());
            this.getFinancialTranInformation().setIntendedPayDate(entity.getIntendedPaymentDate());
            this.getFinancialTranInformation().setTransportDocDate(entity.getTransportDocumentDate());

            if (!AppUtility.isEmpty(entity.getPaymentInformation())) {
                this.setPaymentInformation(new PaymentInformationDTO(entity.getPaymentInformation()));
            }

            if (!AppUtility.isEmpty(entity.getCcData()) && AppConstants.PAYMENT_MODE.IMPORT_CC.equals(entity.getModeOfPayment())) {
                this.setContractCollectionData(new CCDataDTO(entity.getCcData()));
            }

            if (!AppUtility.isEmpty(entity.getLcData()) && AppConstants.PAYMENT_MODE.IMPORT_LC.equals(entity.getModeOfPayment())) {
                this.setLcData(new LCDataDTO(entity.getLcData()));
            }

            if (!AppUtility.isEmpty(entity.getItemInformationSet())) {

                HashSet<ItemInformationDTO> set = new HashSet<>();
                for (ItemInformation item : entity.getItemInformationSet()) {
                    set.add(new ItemInformationDTO(item));
                }
                this.setItemInformation(set);
            }
        }
    }

    @Override
    public FinancialTransactionDTO convertToNewDTO(FinancialTransaction entity, boolean partialFill) {
        FinancialTransactionDTO dto = new FinancialTransactionDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    private class FinTranInformationDTO {
        private Date intendedPayDate;
        private java.util.Date transportDocDate;
        private Date finalDateOfShipment;
    }

}