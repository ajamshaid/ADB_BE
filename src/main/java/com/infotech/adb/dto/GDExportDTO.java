package com.infotech.adb.dto;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class GDExportDTO implements BaseDTO<GDExportDTO, GD> {

     // GD Fields
    private String gdNumber;
    private String gdType;
    private String consignmentCategory;
    private String collectorate;
    private String blAwbNumber;
    private String blAwbDate;
    private String virAirNumber;

    private ConsignorConsigneeDTO consignorConsigneeInfo;

    private GDFinancialInfoDTO financialInformation;

    private GDGeneralInfoDTO generalInformation;
    private Set<ItemInformationExportDTO> itemInformation;


    public GDExportDTO(GD entity) {
        convertToDTO(entity, true);
    }

    @Override
    public GD convertToEntity() {
        GD entity = new GD();
        return entity;
    }

    @Override
    public void convertToDTO(GD entity, boolean partialFill) {

        if (entity != null) {
            this.setGdNumber(entity.getGdNumber());
            this.setGdType(entity.getGdType());
            this.setConsignmentCategory(entity.getConsignmentCategory());
            this.setCollectorate(entity.getCollectorate());
            this.setBlAwbNumber(entity.getBlAwbNumber());
            this.setBlAwbDate(AppUtility.formatedDate(entity.getBlAwbDate()));
            this.setVirAirNumber(entity.getVirAirNumber());

           this.setConsignorConsigneeInfo((new ConsignorConsigneeDTO()).convertToDTO(entity));
           this.setFinancialInformation(new GDFinancialInfoDTO().convertToDTO(entity.getFinancialTransaction()));

           this.setGeneralInformation((new GDGeneralInfoDTO()).convertToDTO(entity));

           // Item Information
            if (!AppUtility.isEmpty(entity.getFinancialTransaction().getItemInformationSet())) {

                HashSet<ItemInformationExportDTO> set = new HashSet<>();
                for (ItemInformation item : entity.getFinancialTransaction().getItemInformationSet()) {
                    set.add(new ItemInformationExportDTO(item));
                }
                this.setItemInformation(set);
            }
        }
    }

    @Override
    public GDExportDTO convertToNewDTO(GD entity, boolean partialFill) {
        GDExportDTO dto = new GDExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }


    @Data
    public class ConsignorConsigneeDTO {
        /// Consignor and Consignee Information
        private String ntnFtn;
        private String strn;
        private String consigneeName;
        private String consigneeAddress;
        private String consignorName;
        private String consignorAddress;

        public ConsignorConsigneeDTO convertToDTO(GD entity) {
            if (entity != null) {
                this.setNtnFtn(entity.getNtnFtn());
                this.setStrn(entity.getStrn());
                this.setConsigneeName(entity.getConsigneeName());
                this.setConsigneeAddress(entity.getConsigneeAddress());
                this.setConsignorName(entity.getConsignorName());
                this.setConsignorAddress(entity.getConsignorAddress());
            }
            return this;
        }
    }

    @Data
    public class GDFinancialInfoDTO {
        private String beneficiaryName;
        private String beneficiaryAddress;
        private String beneficiaryCountry;
        private String beneficiaryIban;

        private String exporterName;
        private String exporterAddress;
        private String exporterCountry;
        private String modeOfPayment;
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

        private String intendedPayDate;
        private String transportDocDate;

        private String invoiceCurrency;
        private String invoiceNumber;
        private String invoiceDate;
        private BigDecimal invoiceValue;
        private String deliveryTerm;
        private BigDecimal fobValueUsd;
        private BigDecimal freightUsd;
        private BigDecimal cfrValueUsd;
        private BigDecimal insuranceUsd;
        private BigDecimal landingChargesUsd;
        private BigDecimal assessedValueUsd;
        private BigDecimal otherCharges;
        private BigDecimal exchangeRate;


        public GDFinancialInfoDTO convertToDTO( FinancialTransaction ft) {
            if (ft != null) {
                PaymentInformation pi = ft.getPaymentInformation();
                this.setBeneficiaryName(pi.getBeneficiaryName());
                this.setBeneficiaryAddress(pi.getBeneficiaryAddress());
                this.setBeneficiaryCountry(pi.getBeneficiaryCountry());
                this.setBeneficiaryIban(pi.getBeneficiaryIban());
                this.setExporterName(pi.getExporterName());
                this.setExporterAddress(pi.getExporterAddress());
                this.setExporterCountry(pi.getExporterCountry());
                this.setModeOfPayment(ft.getModeOfPayment());
                this.setFinInsUniqueNumber(ft.getFinInsUniqueNumber());
                this.setInvoiceValue(pi.getTotalInvoiceValue());
                this.setInvoiceCurrency(pi.getInvoiceCurrency());
                this.setInvoiceNumber(pi.getInvoiceNumber());
                this.setInvoiceDate(AppUtility.formatedDate(pi.getInvoiceDate()));
                this.setDeliveryTerm(pi.getDeliveryTerm());
                this.setFobValueUsd(pi.getFobValueUsd());
                this.setFreightUsd(pi.getFreightUsd());
                this.setCfrValueUsd(pi.getCfrValueUsd());
                this.setInsuranceUsd(pi.getInsuranceUsd());
                this.setLandingChargesUsd(pi.getLandingChargesUsd());
                this.setAssessedValueUsd(pi.getAssessedValueUsd());
                this.setOtherCharges(pi.getOtherCharges());
                this.setExchangeRate(pi.getExchangeRate());


                this.setOpenAccPercentage(ft.getOpenAccPercentage());
                this.setAdvPayPercentage(ft.getAdvPayPercentage());

                if (!AppUtility.isEmpty(ft.getCcData())) {
                    this.setDocAgainstPayPercentage(ft.getCcData().getDocAgainstPayPercentage());
                    this.setDocAgainstAcceptancePercentage(ft.getCcData().getDocAgainstAcceptancePercentage());
                    this.setCcDays(ft.getCcData().getDays());
                }

                if (!AppUtility.isEmpty(ft.getLcData()) ) {
                    this.setSightPercentage(ft.getLcData().getSightPercentage());
                    this.setUsancePercentage(ft.getLcData().getUsancePercentage());
                    this.setLcDays(ft.getLcData().getDays());
                    this.setTotalPercentage(ft.getLcData().getTotalPercentage());
                }
            }
            return this;
        }
    }

    @Data
    public class GDGeneralInfoDTO {
        private Set<GDPackageInfo> packagesInformation;
        private Set<GDContainerVehicleInfo> containerVehicleInformation;
        private String netWeight;
        private String grossWeight;
        private String portOfShipment;
        private String portOfDelivery;
        private String portOfDischarge;
        private String terminalLocation;

        private String consignmentType;
        private String shippingLine;

        public GDGeneralInfoDTO convertToDTO(GD entity) {
            if (entity != null) {
                this.setNetWeight(entity.getNetWeight());
                this.setGrossWeight(entity.getGrossWeight());
                this.setPortOfShipment(entity.getPortOfShipment());
                this.setPortOfDelivery(entity.getPortOfDelivery());
                this.setPortOfDischarge(entity.getPortOfDischarge());
                this.setTerminalLocation(entity.getTerminalLocation());

                this.setConsignmentType(entity.getConsignmentType());
                this.setShippingLine(entity.getShippingLine());

                this.setPackagesInformation(entity.getPackagesInformationSet());
                this.setContainerVehicleInformation(entity.getContainerVehicleInformationSet());
            }
            return this;
        }
    }


}