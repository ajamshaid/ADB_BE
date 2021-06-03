package com.infotech.adb.dto;

import com.infotech.adb.model.entity.PaymentInformation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentInformationImportDTO implements BaseDTO<PaymentInformationImportDTO, PaymentInformation> {

    private Long id;
    private String beneficiaryName;
    private String beneficiaryAddress;
    private String beneficiaryCountry;
    private String beneficiaryIban;

    private String exporterName;
    private String exporterAddress;
    private String exporterCountry;

    private String portOfShipment;
    private String deliveryTerm;
    private BigDecimal financialInstrumentValue;
    private String financialInstrumentCurrency;
    private BigDecimal exchangeRate;
    private String lcContractNo;

    public PaymentInformationImportDTO(PaymentInformation entity) {
        convertToDTO(entity, true);
    }

    @Override
    public PaymentInformation convertToEntity() {
        PaymentInformation entity = new PaymentInformation();
        entity.setId(this.getId());
        entity.setBeneficiaryName(this.getBeneficiaryName());
        entity.setBeneficiaryAddress(this.getBeneficiaryAddress());
        entity.setBeneficiaryCountry(this.getBeneficiaryCountry());
        entity.setBeneficiaryIban(this.getBeneficiaryIban());
        entity.setExporterName(this.getExporterName());
        entity.setExporterAddress(this.getExporterAddress());
        entity.setExporterCountry(this.getExporterCountry());
        entity.setPortOfShipment(this.getPortOfShipment());
        entity.setDeliveryTerm(this.getDeliveryTerm());
        entity.setFinancialInstrumentCurrency(this.getFinancialInstrumentCurrency());
        entity.setFinancialInstrumentValue(this.getFinancialInstrumentValue());
        entity.setLcContractNo(this.getLcContractNo());
        entity.setExchangeRate(this.getExchangeRate());
        return entity;
    }

    @Override
    public void convertToDTO(PaymentInformation entity, boolean partialFill) {

        if (entity != null) {
            this.setId(entity.getId());
            this.setBeneficiaryName(entity.getBeneficiaryName());
            this.setBeneficiaryAddress(entity.getBeneficiaryAddress());
            this.setBeneficiaryCountry(entity.getBeneficiaryCountry());
            this.setBeneficiaryIban(entity.getBeneficiaryIban());
            this.setExporterName(entity.getExporterName());
            this.setExporterAddress(entity.getExporterAddress());
            this.setExporterCountry(entity.getExporterCountry());
            this.setPortOfShipment(entity.getPortOfShipment());
            this.setDeliveryTerm(entity.getDeliveryTerm());
            this.setFinancialInstrumentCurrency(entity.getFinancialInstrumentCurrency());
            this.setFinancialInstrumentValue(entity.getFinancialInstrumentValue());
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