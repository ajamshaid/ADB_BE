package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class CashMarginDTO {

    private String traderNTN;
    private String traderName;
    private String Iban;
    private String cashMarginUniqueIdNumber;
    private BigDecimal invoiceValue;
    private String invoiceNumber;
    private BigDecimal cashMarginPercentage;
    private BigDecimal cashMarginValue;
    private Set<ItemInformationDTO> itemInformation = new HashSet<>();

    public CashMarginDTO(String traderNTN, String traderName, String iban, String cashMarginUniqueIdNumber, BigDecimal invoiceValue, String invoiceNumber, BigDecimal cashMarginPercentage, BigDecimal cashMarginValue) {
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.cashMarginUniqueIdNumber = cashMarginUniqueIdNumber;
        this.invoiceValue = invoiceValue;
        this.invoiceNumber = invoiceNumber;
        this.cashMarginPercentage = cashMarginPercentage;
        this.cashMarginValue = cashMarginValue;
    }


    @Data
    @NoArgsConstructor
    public static class ItemInformationDTO {
        private String hsCode;
        private String goodsDescription;
        private Integer quantity;
        private String uom;
        private String countryOfOrigin;

        public ItemInformationDTO(String hsCode, String goodsDescription, Integer quantity, String uom, String countryOfOrigin) {
            this.hsCode = hsCode;
            this.goodsDescription = goodsDescription;
            this.quantity = quantity;
            this.uom = uom;
            this.countryOfOrigin = countryOfOrigin;
        }
    }
}
