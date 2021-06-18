package com.infotech.adb.dto;

import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class FinancialTransactionExportDTO implements BaseDTO<FinancialTransactionExportDTO, FinancialTransaction> {
    private String exporterNtn;
    private String exporterName;
    private String exporterIban;
    private String modeOfPayment;
    private String finInsUniqueNumber;

    private CCDataDTO contractCollectionData;
    private LCDataDTO lcData;

    private PaymentInformationExportDTO paymentInformation;
    private Set<ItemInformationExportDTO> itemInformation;

    public FinancialTransactionExportDTO(FinancialTransaction entity) {
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
            this.setExporterNtn(entity.getNtn());
            this.setExporterName(entity.getName());
            this.setExporterIban(entity.getIban());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setModeOfPayment(entity.getModeOfPayment());

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
}