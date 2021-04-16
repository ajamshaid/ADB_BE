package com.infotech.adb.dto;

import com.infotech.adb.model.entity.PaymentInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentInformationExportDTO implements BaseDTO<PaymentInformationExportDTO, PaymentInformation> {

    private String beneficiaryName;
    private String beneficiaryAddress;
    private String beneficiaryCountry;
    private String beneficiaryIban;

    private String portOfDischarge;
    private String deliveryTerm;
    private BigDecimal totalValueOfShipment;

    public PaymentInformationExportDTO(PaymentInformation entity) {
        convertToDTO(entity, true);
    }

    @Override
    public PaymentInformation convertToEntity() {
        PaymentInformation entity = new PaymentInformation();
        return entity;
    }

    @Override
    public void convertToDTO(PaymentInformation entity, boolean partialFill) {

        if (entity != null) {
            this.setBeneficiaryName(entity.getBeneficiaryName());
            this.setBeneficiaryAddress(entity.getBeneficiaryAddress());
            this.setBeneficiaryCountry(entity.getBeneficiaryCountry());
            this.setBeneficiaryIban(entity.getBeneficiaryIban());
            this.setPortOfDischarge(entity.getPortOfDischarge());
            this.setDeliveryTerm(entity.getDeliveryTerm());
            this.setTotalValueOfShipment(entity.getTotalValueOfShipment());
        }
    }

    @Override
    public PaymentInformationExportDTO convertToNewDTO(PaymentInformation entity, boolean partialFill) {
        PaymentInformationExportDTO dto = new PaymentInformationExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}