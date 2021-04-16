package com.infotech.adb.dto;

import com.infotech.adb.model.entity.PaymentInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentInformationImportDTO implements BaseDTO<PaymentInformationImportDTO, PaymentInformation> {

    private String beneficiaryName;
    private String beneficiaryAddress;
    private String beneficiaryCountry;
    private String beneficiaryIban;

    private String exporterName;
    private String exporterAddress;
    private String exporterCountry;

    private String portOfShipment;
    private String deliveryTerm;
    private BigDecimal totalInvoiceValue;
    private String invoiceCurrency;
    private BigDecimal exchangeRate;
    private String lcContractNo;

    public PaymentInformationImportDTO(PaymentInformation entity) {
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
            this.setExporterName(entity.getExporterName());
            this.setExporterAddress(entity.getExporterAddress());
            this.setExporterCountry(entity.getExporterCountry());
            this.setPortOfShipment(entity.getPortOfShipment());
            this.setDeliveryTerm(entity.getDeliveryTerm());
            this.setTotalInvoiceValue(entity.getTotalInvoiceValue());
            this.setInvoiceCurrency(entity.getInvoiceCurrency());
            this.setLcContractNo(entity.getLcContractNo());
            this.setExchangeRate(entity.getExchangeRate());
        }
    }

    @Override
    public PaymentInformationImportDTO convertToNewDTO(PaymentInformation entity, boolean partialFill) {
        PaymentInformationImportDTO dto = new PaymentInformationImportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}