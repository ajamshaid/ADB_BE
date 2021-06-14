package com.infotech.adb.dto;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class GDImportDTO implements BaseDTO<GDImportDTO, GD> {

     // GD Fields
    private String gdNumber;
    private String gdType;
    private String gdStatus;
    private String consignmentCategory;
    private String collectorate;
    private String blAwbNumber;
    private String blAwbDate;
    private String virAirNumber;

    private ConsignorConsigneeDTO consignorConsigneeInfo;

    private GDFinancialInfoDTO financialInfo;

    private GDGeneralInfoDTO generalInformation;
    private Set<ItemInformationImportDTO> itemInformation;

    private NegativeListDTO negativeList;


    public GDImportDTO(GD entity) {
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
            this.setGdStatus(entity.getGdStatus());
            this.setConsignmentCategory(entity.getConsignmentCategory());
            this.setCollectorate(entity.getCollectorate());
            this.setBlAwbNumber(entity.getBlAwbNumber());
            this.setBlAwbDate(AppUtility.formatedDate(entity.getBlAwbDate()));
            this.setVirAirNumber(entity.getVirAirNumber());

           this.setConsignorConsigneeInfo((new ConsignorConsigneeDTO()).convertToDTO(entity));
           this.setFinancialInfo((new GDFinancialInfoDTO().convertToDTO(entity.getFinancialTransaction())));

           this.setGeneralInformation((new GDGeneralInfoDTO()).convertToDTO(entity));

           // Item Information
            if (!AppUtility.isEmpty(entity.getFinancialTransaction().getItemInformationSet())) {

                HashSet<ItemInformationImportDTO> set = new HashSet<>();
                for (ItemInformation item : entity.getFinancialTransaction().getItemInformationSet()) {
                    set.add(new ItemInformationImportDTO(item));
                }
                this.setItemInformation(set);
            }
        }
    }

    @Override
    public GDImportDTO convertToNewDTO(GD entity, boolean partialFill) {
        GDImportDTO dto = new GDImportDTO();
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
        private String importerIban;
        private String modeOfPayment;
        private String finInsUniqueNumber;
        private String currency;
        private String invoiceNumber;
        private String invoiceDate;
        private BigDecimal totalDeclaredValue;
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
                this.setImporterIban(ft.getIban());
                this.setModeOfPayment(ft.getModeOfPayment());
                this.setFinInsUniqueNumber(ft.getFinInsUniqueNumber());
                this.setCurrency(pi.getFinancialInstrumentCurrency());

                this.setInvoiceNumber(pi.getInvoiceNumber());
                this.setInvoiceDate(AppUtility.formatedDate(pi.getInvoiceDate()));
                this.setTotalDeclaredValue(pi.getTotalDeclaredValue());

                this.setDeliveryTerm(pi.getDeliveryTerm());
                this.setFobValueUsd(pi.getFobValueUsd());
                this.setFreightUsd(pi.getFreightUsd());
                this.setCfrValueUsd(pi.getCfrValueUsd());
                this.setInsuranceUsd(pi.getInsuranceUsd());
                this.setLandingChargesUsd(pi.getLandingChargesUsd());
                this.setAssessedValueUsd(pi.getAssessedValueUsd());
                this.setOtherCharges(pi.getOtherCharges());
                this.setExchangeRate(pi.getExchangeRate());
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

        public GDGeneralInfoDTO convertToDTO(GD entity) {
            if (entity != null) {
                this.setNetWeight(entity.getNetWeight());
                this.setGrossWeight(entity.getGrossWeight());
                this.setPortOfShipment(entity.getPortOfShipment());
                this.setPortOfDelivery(entity.getPortOfDelivery());
                this.setPortOfDischarge(entity.getPortOfDischarge());
                this.setTerminalLocation(entity.getTerminalLocation());
                this.setPackagesInformation(entity.getPackagesInformationSet());
                this.setContainerVehicleInformation(entity.getContainerVehicleInformationSet());
            }
            return this;
        }
    }

    @Data
    public class NegativeListDTO{
        public String country;
        public String supplier;
        public List<String> commodities;
    }
}