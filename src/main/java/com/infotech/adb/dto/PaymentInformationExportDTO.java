package com.infotech.adb.dto;

import com.infotech.adb.model.entity.PaymentInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class PaymentInformationExportDTO implements BaseDTO<PaymentInformationExportDTO, PaymentInformation> {
    private Long id;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneeCountry;
    private String consigneeIban;

    private String portOfDischarge;
    private String deliveryTerm;
    //private BigDecimal totalValueOfShipment;

    private BigDecimal financialInstrumentValue;
    private String financialInstrumentCurrency;
    private Date expiryDate;

    public PaymentInformationExportDTO(PaymentInformation entity) {
        convertToDTO(entity, true);
    }

    @Override
    public PaymentInformation convertToEntity() {
        PaymentInformation entity = new PaymentInformation();
        entity.setId(this.getId());
        entity.setBeneficiaryName(this.getConsigneeName());
        entity.setBeneficiaryAddress(this.getConsigneeAddress());
        entity.setBeneficiaryCountry(this.getConsigneeCountry());
        entity.setBeneficiaryIban(this.getConsigneeIban());
        entity.setPortOfDischarge(this.getPortOfDischarge());
        entity.setDeliveryTerm(this.getDeliveryTerm());
        entity.setFinancialInstrumentCurrency(this.getFinancialInstrumentCurrency());
        entity.setFinancialInstrumentValue(this.getFinancialInstrumentValue());
        entity.setFinancialInstrumenExpiryDate(this.getExpiryDate());
        return entity;
    }

    @Override
    public void convertToDTO(PaymentInformation entity, boolean partialFill) {

        if (entity != null) {
            this.setId(entity.getId());
            this.setConsigneeName(entity.getBeneficiaryName());
            this.setConsigneeAddress(entity.getBeneficiaryAddress());
            this.setConsigneeCountry(entity.getBeneficiaryCountry());
            this.setConsigneeIban(entity.getBeneficiaryIban());
            this.setPortOfDischarge(entity.getPortOfDischarge());
            this.setDeliveryTerm(entity.getDeliveryTerm());
        //    this.setTotalValueOfShipment(entity.getTotalValueOfShipment());
            this.setFinancialInstrumentCurrency(entity.getFinancialInstrumentCurrency());
            this.setFinancialInstrumentValue(entity.getFinancialInstrumentValue());
            this.setExpiryDate(entity.getFinancialInstrumenExpiryDate());

        }
    }

    @Override
    public PaymentInformationExportDTO convertToNewDTO(PaymentInformation entity, boolean partialFill) {
        PaymentInformationExportDTO dto = new PaymentInformationExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}