package com.infotech.adb.dto;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
public class GDExportDTO implements BaseDTO<GDExportDTO, GDExport> {

    private Long id;
    // GD Fields
    private String gdNumber;
    private String gdType;
    private String gdStatus;

    private String consignmentCategory;
    private String collectorate;
    private String blAwbNumber;
    private Date blAwbDate;
    private String virAirNumber;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private ConsignorConsigneeDTO consignorConsigneeInfo;

    private GDFinancialInfoDTO financialInformation;

    private GDGeneralInfoDTO generalInformation;
    private Set<ItemInformationExportDTO> itemInformation;


    private NegativeListDTO negativeList;

    public GDExportDTO(GDExport entity) {
        convertToDTO(entity, true);
    }

    @Override
    public GDExport convertToEntity() {
        GDExport entity = new GDExport();

        entity.setId(this.getId());
        entity.setGdNumber(this.getGdNumber());
        entity.setGdType(this.getGdType());
        entity.setGdStatus(this.getGdStatus());
        entity.setConsignmentCategory(this.getConsignmentCategory());
        entity.setCollectorate(this.getCollectorate());
        entity.setBlAwbNumber(this.getBlAwbNumber());
        entity.setBlAwbDate(this.getBlAwbDate());
        entity.setVirAirNumber(this.getVirAirNumber());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());

        //Consignor Info
        if(!AppUtility.isEmpty(this.getConsignorConsigneeInfo())) {
            entity.setNtnFtn(this.getConsignorConsigneeInfo().getNtnFtn());
            entity.setStrn(this.getConsignorConsigneeInfo().getStrn());
            entity.setConsigneeName(this.getConsignorConsigneeInfo().getConsigneeName());
            entity.setConsigneeAddress(this.getConsignorConsigneeInfo().getConsigneeAddress());
            entity.setConsignorName(this.getConsignorConsigneeInfo().getConsignorName());
            entity.setConsignorAddress(this.getConsignorConsigneeInfo().getConsignorAddress());
        }

        // Financial Information
        if(!AppUtility.isEmpty(this.getFinancialInformation())) {
            entity.setCurrency(this.getFinancialInformation().getCurrency());
            entity.setInvoiceNumber(this.getFinancialInformation().getInvoiceNumber());
            entity.setInvoiceDate(this.getFinancialInformation().getInvoiceDate());//AppUtility.formatedDate(pi.getInvoiceDate()));
            entity.setTotalDeclaredValue(this.getFinancialInformation().getTotalDeclaredValue());
            entity.setDeliveryTerm(this.getFinancialInformation().getDeliveryTerm());
            entity.setFobValueUsd(this.getFinancialInformation().getFobValueUsd());
            entity.setFreightUsd(this.getFinancialInformation().getFreightUsd());
            entity.setCfrValueUsd(this.getFinancialInformation().getCfrValueUsd());
            entity.setInsuranceUsd(this.getFinancialInformation().getInsuranceUsd());
            entity.setLandingChargesUsd(this.getFinancialInformation().getLandingChargesUsd());
            entity.setAssessedValueUsd(this.getFinancialInformation().getAssessedValueUsd());
            entity.setOtherCharges(this.getFinancialInformation().getOtherCharges());
            entity.setExchangeRate(this.getFinancialInformation().getExchangeRate());


            //Financial Instruments.
            if (!AppUtility.isEmpty(this.getFinancialInformation().getFinancialInstrument())) {
                HashSet<GDFinancialInstrument> set = new HashSet<>();
                for (GDFinancialInstrumentDTO dto : this.getFinancialInformation().getFinancialInstrument()) {
                    GDFinancialInstrument fi = new GDFinancialInstrument();
                    fi.setFinInsUniqueNumber(dto.getFinInsUniqueNumber());
                    fi.setIban(dto.getExporterIban());
                    fi.setModeOfPayment(dto.getModeOfPayment());
                    fi.setGdExport(entity);
                    set.add(fi);
                }
                entity.setGdFinancialInstrumentSet(set);
            }
        }

        // General Information
        if(!AppUtility.isEmpty(this.getGeneralInformation())){
            entity.setNetWeight(this.getGeneralInformation().getNetWeight());
            entity.setGrossWeight(this.getGeneralInformation().getGrossWeight());
            entity.setPortOfShipment(this.getGeneralInformation().getPortOfShipment());
            entity.setPlaceOfDelivery(this.getGeneralInformation().getPlaceOfDelivery());
            entity.setPortOfDischarge(this.getGeneralInformation().getPortOfDischarge());
            entity.setTerminalLocation(this.getGeneralInformation().getTerminalLocation());
            entity.setConsignmentType(this.getGeneralInformation().getConsignmentType());
            entity.setShippingLine(this.getGeneralInformation().getShippingLine());

            entity.setPackagesInformationSet(this.getGeneralInformation().getPackagesInformation());
            entity.setContainerVehicleInformationSet(this.getGeneralInformation().getContainerVehicleInformation());

            for (GDContainerVehicleInfo vi : entity.getContainerVehicleInformationSet()) {
                vi.setGdExport(entity);
            }
            for (GDPackageInfo pkg: entity.getPackagesInformationSet()) {
                pkg.setGdExport(entity);
            }
        }
        //item Information
        if (!AppUtility.isEmpty(this.getItemInformation())) {
            HashSet<ItemInformation> set = new HashSet<>();
            for (ItemInformationExportDTO itemDTO : this.getItemInformation()) {
                ItemInformation item = itemDTO.convertToEntity();
                item.setGdExport(entity);
                set.add(item);
            }
            entity.setItemInformationSet(set);
        }

        if(!AppUtility.isEmpty(this.getNegativeList())){
            entity.setNegativeCountry(this.getNegativeList().getCountry());
            entity.setNegativeSupplier(this.getNegativeList().getSupplier());
            if(!AppUtility.isEmpty(this.getNegativeList().getCommodities())){
                entity.setNegativeCommodities(this.getNegativeList().getCommodities().toString());
            }
        }
    return entity;
    }

    @Override
    public void convertToDTO(GDExport entity, boolean partialFill) {

        if (entity != null) {
            this.setId(entity.getId());
            this.setGdNumber(entity.getGdNumber());
            this.setGdType(entity.getGdType());
            this.setGdStatus(entity.getGdStatus());
            this.setConsignmentCategory(entity.getConsignmentCategory());
            this.setCollectorate(entity.getCollectorate());
            this.setBlAwbNumber(entity.getBlAwbNumber());
            this.setBlAwbDate(entity.getBlAwbDate());//AppUtility.formatedDate(entity.getBlAwbDate()));
            this.setVirAirNumber(entity.getVirAirNumber());

            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

            this.setConsignorConsigneeInfo((new ConsignorConsigneeDTO()).convertToDTO(entity));
            this.setFinancialInformation(new GDFinancialInfoDTO().convertToDTO(entity));
            this.setGeneralInformation((new GDGeneralInfoDTO()).convertToDTO(entity));

            NegativeListDTO negativeListDTO = new NegativeListDTO(entity.getNegativeCountry()
                    , entity.getNegativeSupplier()
                    ,entity.getNegativeCommodities());
            this.setNegativeList(negativeListDTO);

            // Item Information
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
    public GDExportDTO convertToNewDTO(GDExport entity, boolean partialFill) {
        GDExportDTO dto = new GDExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

    @Data
    @NoArgsConstructor
    public class ConsignorConsigneeDTO {
        /// Consignor and Consignee Information
        private String ntnFtn;
        private String strn;
        private String consigneeName;
        private String consigneeAddress;
        private String consignorName;
        private String consignorAddress;

        public ConsignorConsigneeDTO convertToDTO(GDExport entity) {
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
    @NoArgsConstructor
    public class GDFinancialInfoDTO {

        private String currency;
        private String invoiceNumber;
        private Date invoiceDate;
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
        private Set<GDFinancialInstrumentDTO> financialInstrument;

        public GDFinancialInfoDTO convertToDTO( GDExport entity) {
            if (entity != null) {
                this.setCurrency(entity.getCurrency());
                this.setInvoiceNumber(entity.getInvoiceNumber());
                this.setInvoiceDate(entity.getInvoiceDate());//AppUtility.formatedDate(pi.getInvoiceDate()));
                this.setTotalDeclaredValue(entity.getTotalDeclaredValue());
                this.setDeliveryTerm(entity.getDeliveryTerm());
                this.setFobValueUsd(entity.getFobValueUsd());
                this.setFreightUsd(entity.getFreightUsd());
                this.setCfrValueUsd(entity.getCfrValueUsd());
                this.setInsuranceUsd(entity.getInsuranceUsd());
                this.setLandingChargesUsd(entity.getLandingChargesUsd());
                this.setAssessedValueUsd(entity.getAssessedValueUsd());
                this.setOtherCharges(entity.getOtherCharges());
                this.setExchangeRate(entity.getExchangeRate());

                // Financial Instrument..
                if (!AppUtility.isEmpty(entity.getGdFinancialInstrumentSet())) {
                    HashSet<GDFinancialInstrumentDTO> fiSet = new HashSet<>();
                    for (GDFinancialInstrument gdFinancialInstrument : entity.getGdFinancialInstrumentSet()) {
                        GDFinancialInstrumentDTO dto = new GDFinancialInstrumentDTO();
                        dto.setExporterIban(gdFinancialInstrument.getIban());
                        dto.setFinInsUniqueNumber(gdFinancialInstrument.getFinInsUniqueNumber());
                        dto.setModeOfPayment(gdFinancialInstrument.getModeOfPayment());
                        fiSet.add(dto);
                    }
                    this.setFinancialInstrument(fiSet);
                }
            }
            return this;
        }

    }
    @Data
    public static class  GDFinancialInstrumentDTO {
        private String exporterIban;
        private String modeOfPayment;
        private String finInsUniqueNumber;

        public GDFinancialInstrumentDTO() {
        }

        public GDFinancialInstrumentDTO(String exporterIban, String modeOfPayment, String finInsUniqueNumber) {
            this.exporterIban = exporterIban;
            this.modeOfPayment = modeOfPayment;
            this.finInsUniqueNumber = finInsUniqueNumber;
        }
    }

    @Data
    @NoArgsConstructor
    public class NegativeListDTO{
        public String country;
        public String supplier;
        public List<String> commodities;

        public NegativeListDTO(String country, String supplier, String commodities) {
            this.country = country;
            this.supplier = supplier;

            if(!AppUtility.isEmpty(commodities)){
                String [] comAry = commodities.split(" ");
                if(!AppUtility.isEmpty(comAry)){
                    this.commodities = Arrays.asList(comAry);
                }
            }else {
                this.commodities = new ArrayList<>();
            }

        }
    }

    @Data
    @NoArgsConstructor
    public class GDGeneralInfoDTO {
        private Set<GDPackageInfo> packagesInformation;
        private Set<GDContainerVehicleInfo> containerVehicleInformation;
        private String netWeight;
        private String grossWeight;
        private String portOfShipment;
        private String placeOfDelivery;
        private String portOfDischarge;
        private String terminalLocation;

        private String consignmentType;
        private String shippingLine;

        public GDGeneralInfoDTO convertToDTO(GDExport entity) {
            if (entity != null) {
                this.setNetWeight(entity.getNetWeight());
                this.setGrossWeight(entity.getGrossWeight());
                this.setPortOfShipment(entity.getPortOfShipment());
                this.setPlaceOfDelivery(entity.getPlaceOfDelivery());
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