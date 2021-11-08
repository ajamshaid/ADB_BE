package com.infotech.adb.dto;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
public class GDImportDTO implements BaseDTO<GDImportDTO, GD> {

     // GD Fields
     private Long id;
    private String gdNumber;
    private String gdType;
    private String gdStatus;
    private String consignmentCategory;
    private String collectorate;
    private String blAwbNumber;
    private String blAwbDate;
    private String virAirNumber;
    private String lastModifiedBy;
    private Date lastModifiedDate;

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
        entity.setGdNumber(this.getGdNumber());
        entity.setGdType(this.getGdType());
        entity.setGdStatus(this.getGdStatus());
        entity.setConsignmentCategory(this.getConsignmentCategory());
        entity.setCollectorate(this.getCollectorate());
        entity.setBlAwbNumber(this.getBlAwbNumber());
        entity.setBlAwbDate(AppUtility.convertDateFromString(this.getBlAwbDate()));
        entity.setVirAirNumber(this.getVirAirNumber());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());

        //Consignor Info
        entity.setNtnFtn(this.getConsignorConsigneeInfo().getNtnFtn());
        entity.setStrn(this.getConsignorConsigneeInfo().getStrn());
        entity.setConsigneeName(this.getConsignorConsigneeInfo().getConsigneeName());
        entity.setConsigneeAddress(this.getConsignorConsigneeInfo().getConsigneeAddress());
        entity.setConsignorName(this.getConsignorConsigneeInfo().getConsignorName());
        entity.setConsignorAddress(this.getConsignorConsigneeInfo().getConsignorAddress());

        if(!AppUtility.isEmpty(this.getFinancialInfo())) {
            // Financial Info
            PaymentInformation pi = new PaymentInformation();
            FinancialTransaction ft = new FinancialTransaction();

            ft.setStatus(AppConstants.RecordStatuses.CREATED_BY_PSW);
            ft.setType(AppConstants.TYPE_GD_IMPORT);

            ft.setIban(this.getFinancialInfo().getImporterIban());

            if(!AppUtility.isEmpty(this.getFinancialInfo().getModeOfPayment()) &&
            AppConstants.PAYMENT_MODE.LETTER_OF_CREDIT.equalsIgnoreCase(this.getFinancialInfo().getModeOfPayment())){
                ft.setModeOfPayment(AppConstants.PAYMENT_MODE.IMP_LC_VALUE);
            }else{
                ft.setModeOfPayment(this.getFinancialInfo().getModeOfPayment());
            }


            ft.setFinInsUniqueNumber(this.getFinancialInfo().getFinInsUniqueNumber());

            pi.setFinancialInstrumentCurrency(this.getFinancialInfo().getCurrency());
            pi.setInvoiceNumber(this.getFinancialInfo().getInvoiceNumber());
            pi.setInvoiceDate(AppUtility.convertDateFromString(this.getFinancialInfo().getInvoiceDate()));
            pi.setTotalDeclaredValue(this.getFinancialInfo().getTotalDeclaredValue());

            pi.setDeliveryTerm(this.getFinancialInfo().getDeliveryTerm());
            pi.setFobValueUsd(this.getFinancialInfo().getFobValueUsd());
            pi.setFreightUsd(this.getFinancialInfo().getFreightUsd());
            pi.setCfrValueUsd(this.getFinancialInfo().getCfrValueUsd());
            pi.setInsuranceUsd(this.getFinancialInfo().getInsuranceUsd());
            pi.setLandingChargesUsd(this.getFinancialInfo().getLandingChargesUsd());
            pi.setAssessedValueUsd(this.getFinancialInfo().getAssessedValueUsd());
            pi.setOtherCharges(this.getFinancialInfo().getOtherCharges());
            pi.setExchangeRate(this.getFinancialInfo().getExchangeRate());

            ft.setPaymentInformation(pi);
            pi.setFinancialTransaction(ft);

            //item Information
            if (!AppUtility.isEmpty(this.getItemInformation())) {
                HashSet<ItemInformation> set = new HashSet<>();
                for (ItemInformationImportDTO itemDTO : this.getItemInformation()) {
                    ItemInformation item = itemDTO.convertToEntity();
                    item.setFinancialTransaction(ft);
                    set.add(item);
                }
                ft.setItemInformationSet(set);
            }
            entity.setFinancialTransaction(ft);
        }

        //General Informaion
        if(!AppUtility.isEmpty(this.getGeneralInformation())) {
            entity.setNetWeight(this.getGeneralInformation().getNetWeight());
            entity.setGrossWeight(this.getGeneralInformation().getGrossWeight());
            entity.setPortOfShipment(this.getGeneralInformation().getPortOfShipment());
            entity.setPortOfDelivery(this.getGeneralInformation().getPortOfDelivery());
            entity.setPortOfDischarge(this.getGeneralInformation().getPortOfDischarge());
            entity.setTerminalLocation(this.getGeneralInformation().getTerminalLocation());
            entity.setPackagesInformationSet(this.getGeneralInformation().getPackagesInformation());
            entity.setContainerVehicleInformationSet(this.getGeneralInformation().getContainerVehicleInformation());

            for (GDContainerVehicleInfo vi : entity.getContainerVehicleInformationSet()) {
                vi.setGd(entity);
            }
            for (GDPackageInfo pkg: entity.getPackagesInformationSet()) {
                pkg.setGd(entity);
            }
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
    public void convertToDTO(GD entity, boolean partialFill) {

        if (entity != null) {
            this.setId(entity.getId());
            this.setGdNumber(entity.getGdNumber());
            this.setGdType(entity.getGdType());
            this.setGdStatus(entity.getGdStatus());
            this.setConsignmentCategory(entity.getConsignmentCategory());
            this.setCollectorate(entity.getCollectorate());
            this.setBlAwbNumber(entity.getBlAwbNumber());
            this.setBlAwbDate(AppUtility.formatedDate(entity.getBlAwbDate()));
            this.setVirAirNumber(entity.getVirAirNumber());
            this.setLastModifiedBy(entity.getLastModifiedBy());
            this.setLastModifiedDate(entity.getLastModifiedDate());

           this.setConsignorConsigneeInfo((new ConsignorConsigneeDTO()).convertToDTO(entity));
           this.setFinancialInfo((new GDFinancialInfoDTO().convertToDTO(entity.getFinancialTransaction())));

           this.setGeneralInformation((new GDGeneralInfoDTO()).convertToDTO(entity));

            NegativeListDTO negativeListDTO = new NegativeListDTO(entity.getNegativeCountry()
                    , entity.getNegativeSupplier()
                    ,entity.getNegativeCommodities());
           this.setNegativeList(negativeListDTO);

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
}