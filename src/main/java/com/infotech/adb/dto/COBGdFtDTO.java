package com.infotech.adb.dto;

import com.infotech.adb.model.entity.COBGdFt;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class COBGdFtDTO implements BaseDTO<COBGdFtDTO, COBGdFt> {
    private  Long id;
    private String cobUniqueIdNumber;
    private String tradeTranType;

    private FinancialTransactionImportDTO financialInstrumentInfo;
    private GDImportDTO gdImport;
    private BDADTO bdaInfo;

    @Override
    public COBGdFt convertToEntity() {
        COBGdFt entity = new COBGdFt();
        entity.setId(this.getId());
        entity.setCobUniqueIdNumber((this.getCobUniqueIdNumber()));
        entity.setTradeTranType(this.getTradeTranType());
        entity.setFt(this.financialInstrumentInfo.convertToEntity());
        entity.setGd(this.gdImport.convertToEntity());
        entity.setBda(this.bdaInfo.convertToEntity());

      return entity;
    }

    @Override
    public void convertToDTO(COBGdFt entity, boolean partialFill) {

        this.id = entity.getId();
        this.cobUniqueIdNumber = entity.getCobUniqueIdNumber();
        this.tradeTranType = entity.getTradeTranType();

        if (!AppUtility.isEmpty(entity.getFt())) {
            this.financialInstrumentInfo = new FinancialTransactionImportDTO(entity.getFt());
        }

        if(!AppUtility.isEmpty(entity.getGd())){
            this.gdImport = new GDImportDTO(entity.getGd());
        }

        if(!AppUtility.isEmpty(entity.getBda())){
            this.bdaInfo = new BDADTO();
            this.bdaInfo= this.bdaInfo.convertToNewDTO(entity.getBda(),true);
        }

    }

    @Override
    public COBGdFtDTO convertToNewDTO(COBGdFt entity, boolean partialFill) {
        COBGdFtDTO dto = new COBGdFtDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }

}