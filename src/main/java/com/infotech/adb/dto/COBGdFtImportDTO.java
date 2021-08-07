package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BDA;
import com.infotech.adb.model.entity.COBGdFt;
import com.infotech.adb.model.entity.GD;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class COBGdFtImportDTO extends COBGdFtDTO {
    private FinancialTransactionImportDTO financialInstrumentInfo;
    private Set<GDImportDTO> gdInfo;
    private Set<BDADTO> bankAdviceInfo;

    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Override
    public COBGdFt convertToEntity() {
        COBGdFt entity = new COBGdFt();
        entity.setId(this.getId());
        entity.setCobUniqueIdNumber((this.getCobUniqueIdNumber()));
        entity.setTradeTranType(this.getTradeTranType());
        entity.setFt(this.financialInstrumentInfo.convertToEntity());
        entity.setLastModifiedBy(this.getLastModifiedBy());
        entity.setLastModifiedDate(this.getLastModifiedDate());

        entity.getFt().setType("COB_IMPORT");

        // GD Sets
        if (!AppUtility.isEmpty(this.getGdInfo())) {
            HashSet<GD> set = new HashSet<>();
            for (GDImportDTO dto : this.getGdInfo()) {
                GD en = dto.convertToEntity();
                en.setCobGdFt(entity);
                set.add(en);
            }
            entity.setGdSet(set);
        }

        // Bank Advice Sets
        if (!AppUtility.isEmpty(this.getBankAdviceInfo())) {
            HashSet<BDA> set = new HashSet<>();
            for (BDADTO dto : this.getBankAdviceInfo()) {
                BDA en = dto.convertToEntity();
                en.setCobGdFt(entity);
                set.add(en);
            }
            entity.setBdaSet(set);
        }
      return entity;
    }

    @Override
    public void convertToDTO(COBGdFt entity, boolean partialFill) {

        this.id = entity.getId();
        this.cobUniqueIdNumber = entity.getCobUniqueIdNumber();
        this.tradeTranType = entity.getTradeTranType();

        this.setLastModifiedBy(entity.getLastModifiedBy());
        this.setLastModifiedDate(entity.getLastModifiedDate());


        if (!AppUtility.isEmpty(entity.getFt())) {
            this.financialInstrumentInfo = new FinancialTransactionImportDTO(entity.getFt());
        }

        // Import GDs
        if (!AppUtility.isEmpty(entity.getGdSet())) {
            HashSet<GDImportDTO> set = new HashSet<>();
            for (GD gd : entity.getGdSet()) {
                set.add(new GDImportDTO(gd));
            }
            this.setGdInfo(set);
        }

        // Import GDs
        if (!AppUtility.isEmpty(entity.getBdaSet())) {
            HashSet<BDADTO> set = new HashSet<>();
            for (BDA ref : entity.getBdaSet()) {
                set.add(new BDADTO(ref));
            }
            this.setBankAdviceInfo(set);
        }
    }

    @Override
    public COBGdFtImportDTO convertToNewDTO(COBGdFt entity, boolean partialFill) {
        COBGdFtImportDTO dto = new COBGdFtImportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}