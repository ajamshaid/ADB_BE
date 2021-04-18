package com.infotech.adb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class TradeTransSettlementDTO extends TradeTransactionDTO {

    private List<String> bcaBdaUniqueIdNumber;
    private BigDecimal finInsValue;
    private BigDecimal totalValueOfSharedBCABDA;
    private BigDecimal balance;

    public TradeTransSettlementDTO(String tradeTranType, String traderNTN, String traderName
            , String iban, String finInsUniqueNumber, List<String> bcaBdaUniqueIdNumber
            , BigDecimal finInsValue, BigDecimal totalValueOfSharedBCABDA, BigDecimal balance) {
        super(tradeTranType, traderNTN, traderName, iban, finInsUniqueNumber);
        this.bcaBdaUniqueIdNumber = bcaBdaUniqueIdNumber;
        this.finInsValue = finInsValue;
        this.totalValueOfSharedBCABDA = totalValueOfSharedBCABDA;
        this.balance = balance;
    }
}
