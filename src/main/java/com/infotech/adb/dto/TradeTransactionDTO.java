package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeTransactionDTO {

    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String finInsUniqueNumber;

    public TradeTransactionDTO(String tradeTranType, String traderNTN, String traderName, String iban, String finInsUniqueNumber) {
        this.tradeTranType = tradeTranType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.finInsUniqueNumber = finInsUniqueNumber;
    }
}
