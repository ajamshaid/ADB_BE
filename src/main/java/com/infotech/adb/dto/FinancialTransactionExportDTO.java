package com.infotech.adb.dto;

import com.infotech.adb.model.entity.FinTransMOP;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class FinancialTransactionExportDTO implements BaseDTO<FinancialTransactionExportDTO, FinancialTransaction> {
    private String exporterNtn;
    private String exporterName;

    private HashSet<String> modeOfPayment;
    private String finInsUniqueNumber;

    private BigDecimal openAccPercentage;
    private BigDecimal advPayPercentage;
    private BigDecimal docAgainstPayPercentage;
    private BigDecimal docAgainstAcceptancePercentage;
    private Integer ccDays;

    private BigDecimal sightPercentage;
    private BigDecimal usancePercentage;
    private Integer lcDays;

    private BigDecimal totalPercentage;

    private PaymentInformationExportDTO paymentInformation;

    private Set<ItemInformationImportDTO> itemInformation;

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
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());

            if (!AppUtility.isEmpty(entity.getPaymentInformation())) {
                this.setPaymentInformation(new PaymentInformationExportDTO(entity.getPaymentInformation()));
            }

//            this.setOpenAccPercentage(entity.getOpenAccPercentage());
//            this.setAdvPayPercentage(entity.getAdvPayPercentage());

            if (!AppUtility.isEmpty(entity.getCcData())) {
                this.setDocAgainstPayPercentage(entity.getCcData().getDocAgainstPayPercentage());
                this.setDocAgainstAcceptancePercentage(entity.getCcData().getDocAgainstAcceptancePercentage());
                this.setCcDays(entity.getCcData().getDays());
            }

            if (!AppUtility.isEmpty(entity.getLcData()) ) {
                this.setSightPercentage(entity.getLcData().getSightPercentage());
                this.setUsancePercentage(entity.getLcData().getUsancePercentage());
                this.setLcDays(entity.getLcData().getDays());
                this.setTotalPercentage(entity.getLcData().getTotalPercentage());
            }

            //Fin Trans mop SET
            HashSet<String> mopSet = new HashSet<>();
            for (FinTransMOP item : entity.getFinTransMOPSet()) {
                mopSet.add(item.getModeOfPayment());
            }
            this.setModeOfPayment(mopSet);

            //Item Information
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
    public FinancialTransactionExportDTO convertToNewDTO(FinancialTransaction entity, boolean partialFill) {
        FinancialTransactionExportDTO dto = new FinancialTransactionExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}