package com.infotech.adb.dto;

import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.COBGdFt;
import com.infotech.adb.model.entity.GDExport;
import com.infotech.adb.util.AppUtility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class COBGdFtExportDTO extends COBGdFtDTO {
    private FinancialTransactionExportDTO financialInstrumentInfo;
    private Set<GDExportDTO> gdInfo;
    private Set<BCADTO> bankAdviceInfo;

    @Override
    public COBGdFt convertToEntity() {
        COBGdFt entity = new COBGdFt();
        entity.setId(this.getId());
        entity.setCobUniqueIdNumber((this.getCobUniqueIdNumber()));
        entity.setTradeTranType(this.getTradeTranType());
        entity.setFt(this.financialInstrumentInfo.convertToEntity());
        entity.getFt().setType("COB_EXPORT");

        // GD Sets
        if (!AppUtility.isEmpty(this.getGdInfo())) {
            HashSet<GDExport> set = new HashSet<>();
            for (GDExportDTO dto : this.getGdInfo()) {
                GDExport en = dto.convertToEntity();
                en.setCobGdFt(entity);
                set.add(en);
            }
            entity.setGdExportSet(set);
        }

        // Bank Advice Sets
        if (!AppUtility.isEmpty(this.getBankAdviceInfo())) {
            HashSet<BCA> set = new HashSet<>();
            for (BCADTO dto : this.getBankAdviceInfo()) {
                BCA en = dto.convertToEntity();
                en.setCobGdFt(entity);
                set.add(en);
            }
            entity.setBcaSet(set);
        }
      return entity;
    }

    @Override
    public void convertToDTO(COBGdFt entity, boolean partialFill) {

        this.id = entity.getId();
        this.cobUniqueIdNumber = entity.getCobUniqueIdNumber();
        this.tradeTranType = entity.getTradeTranType();

        if (!AppUtility.isEmpty(entity.getFt())) {
            this.financialInstrumentInfo = new FinancialTransactionExportDTO(entity.getFt());
        }

        // Export GDs
        if (!AppUtility.isEmpty(entity.getGdExportSet())) {
            HashSet<GDExportDTO> set = new HashSet<>();
            for (GDExport gd : entity.getGdExportSet()) {
                set.add(new GDExportDTO(gd));
            }
            this.setGdInfo(set);
        }

        // Export BCA
        if (!AppUtility.isEmpty(entity.getBcaSet())) {
            HashSet<BCADTO> set = new HashSet<>();
            for (BCA ref : entity.getBcaSet()) {
                set.add(new BCADTO(ref));
            }
            this.setBankAdviceInfo(set);
        }
    }

    @Override
    public COBGdFtExportDTO convertToNewDTO(COBGdFt entity, boolean partialFill) {
        COBGdFtExportDTO dto = new COBGdFtExportDTO();
        dto.convertToDTO(entity, partialFill);
        return dto;
    }
}