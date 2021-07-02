package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ReversalOfBdaBca;
import com.infotech.adb.model.entity.SettelmentOfFI;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettelmentOfFIDTO implements BaseDTO<SettelmentOfFIDTO, SettelmentOfFI> {

    private Long id;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String finInsUniqueNumber;
    private String bdaBcaUniqueIdNumber;
    private String finInsValue;
    private String totalValueOfSharedBCABDA;
    private String balance;

    public SettelmentOfFIDTO(String id , String tradeType, String traderNTN, String traderName,  String finInsUniqueNumber) {
        this.tradeTranType = tradeType;
        this.traderNTN = traderNTN;
        this.traderName = traderName;
        this.finInsUniqueNumber = finInsUniqueNumber;

    }


    @Override
    public SettelmentOfFI convertToEntity() {
        SettelmentOfFI entity = new SettelmentOfFI();

        entity.setId(this.getId());
        entity.setBdaBcaUniqueIdNumber(this.getBdaBcaUniqueIdNumber());
        entity.setTradeType(this.getTradeTranType());
        entity.setTraderNTN(this.getTraderNTN());
        entity.setTraderName(this.getTraderName());
        entity.setFinInsUniqueNumber(this.getFinInsUniqueNumber());
        entity.setFinInsValue(this.getFinInsValue());
        entity.setBalance(this.getBalance());
        entity.setTotalValueOfSharedBCABDA(this.getTotalValueOfSharedBCABDA());

        return entity;
    }

    @Override
    public void convertToDTO(SettelmentOfFI entity, boolean partialFill) {
        if(entity != null){

            this.setId(entity.getId());
            this.setBdaBcaUniqueIdNumber(entity.getBdaBcaUniqueIdNumber());
            this.setFinInsUniqueNumber(entity.getFinInsUniqueNumber());
            this.setTradeTranType(entity.getTradeType());
            this.setTraderName(entity.getTraderName());
            this.setTraderNTN(entity.getTraderNTN());
            this.setFinInsValue(entity.getFinInsValue());
            this.setBalance(entity.getBalance());
            this.setTotalValueOfSharedBCABDA(entity.getTotalValueOfSharedBCABDA());

        }
    }

    @Override
    public SettelmentOfFIDTO convertToNewDTO(SettelmentOfFI entity, boolean partialFill) {
        SettelmentOfFIDTO dto = new SettelmentOfFIDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}
