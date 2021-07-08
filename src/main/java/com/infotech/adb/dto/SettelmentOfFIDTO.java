package com.infotech.adb.dto;

import com.infotech.adb.model.entity.SettelmentOfFI;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SettelmentOfFIDTO implements BaseDTO<SettelmentOfFIDTO, SettelmentOfFI> {

    private Long id;
    private String tradeTranType;
    private String traderNTN;
    private String traderName;
    private String finInsUniqueNumber;
    private List<String> bdaBcaUniqueIdNumber;
    private BigDecimal finInsValue;
    private BigDecimal totalValueOfSharedBCABDA;
    private BigDecimal balance;

    @Override
    public SettelmentOfFI convertToEntity() {
        SettelmentOfFI entity = new SettelmentOfFI();

        entity.setId(this.getId());
        entity.setBdaBcaUniqueIdNumber(this.getBdaBcaUniqueIdNumber().get(0));
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
            List<String> unqNumList = new ArrayList<>();

            unqNumList.add(entity.getBdaBcaUniqueIdNumber());

            this.setBdaBcaUniqueIdNumber(unqNumList);
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
