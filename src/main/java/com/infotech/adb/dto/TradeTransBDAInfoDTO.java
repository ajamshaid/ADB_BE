package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeTransBDAInfoDTO extends TradeTransactionDTO_TODEL {
    private String bcaBdaUniqueIdNumber;
    private String gdNumber;

    public TradeTransBDAInfoDTO(String tradeTranType, String traderNTN, String traderName, String iban, String finInsUniqueNumber, String bcaBdaUniqueIdNumber, String gdNumber) {
        super(tradeTranType, traderNTN, traderName, iban, finInsUniqueNumber);
        this.bcaBdaUniqueIdNumber = bcaBdaUniqueIdNumber;
        this.gdNumber = gdNumber;
    }
}
