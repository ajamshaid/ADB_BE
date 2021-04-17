package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeBankRequestDTO {

    private String cobUniqueIdNumber;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String Iban;
    private String finInsUniqueNumber;
    private String gdNumber;
    private String cobStatus;

    public ChangeBankRequestDTO(String cobUniqueIdNumber, String tradeTranType, String traderNTN, String traderName, String iban, String finInsUniqueNumber, String gdNumber) {
        this.cobUniqueIdNumber = cobUniqueIdNumber;
        this.tradeTranType = tradeTranType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        Iban = iban;
        this.finInsUniqueNumber = finInsUniqueNumber;
        this.gdNumber = gdNumber;
    }
}
