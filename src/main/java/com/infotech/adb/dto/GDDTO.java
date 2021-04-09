package com.infotech.adb.dto;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class GDDTO implements BaseDTO<GDDTO, GD> {

     // GD Fields
    private String gdNumber;
    private String gdType;
    private String consignmentCategory;
    private String collectorate;
    private String blAwbNumber;
    private String blAwbDate;
    private String virAirNumber;

    private ConsignorConsigneeDTO consignorConsigneeInfo;

    private GDFinancialInfoDTO financialInfo;

    private GDGeneralInfoDTO generalInformation;
    private Set<ItemInformationDTO> itemInformation;


    public GDDTO(GD entity) {
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
           this.setFinancialInfo((new GDFinancialInfoDTO().convertToDTO(entity.getFinancialTransaction())));

           this.setGeneralInformation((new GDGeneralInfoDTO()).convertToDTO(entity));

           // Item Information
            if (!AppUtility.isEmpty(entity.getFinancialTransaction().getItemInformationSet())) {

                HashSet<ItemInformationDTO> set = new HashSet<>();
                for (ItemInformation item : entity.getFinancialTransaction().getItemInformationSet()) {
                    set.add(new ItemInformationDTO(item));
                }
                this.setItemInformation(set);
            }
        }
    }

    @Override
    public GDDTO convertToNewDTO(GD entity, boolean partialFill) {
        GDDTO dto = new GDDTO();
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

        private CCDataDTO contractCollectionData;
        private LCDataDTO lcData;

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

                if (!AppUtility.isEmpty(ft.getCcData()) && AppConstants.PAYMENT_MODE.IMPORT_CC.equals(ft.getModeOfPayment())) {
                    this.setContractCollectionData(new CCDataDTO(ft.getCcData()));
                }

                if (!AppUtility.isEmpty(ft.getLcData()) && AppConstants.PAYMENT_MODE.IMPORT_LC.equals(ft.getModeOfPayment())) {
                    this.setLcData(new LCDataDTO(ft.getLcData()));
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


}