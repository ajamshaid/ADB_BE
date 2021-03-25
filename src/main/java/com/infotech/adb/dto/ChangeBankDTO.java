package com.infotech.adb.dto;

import com.infotech.adb.model.entity.ChangeBank;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ChangeBankDTO implements BaseDTO<ChangeBankDTO, ChangeBank> {

    private String exporterNTN;
    private String exporterName;
    private String exportPaymentMode;
    private String paymentIdentificationNo;

    @Override
    public ChangeBank convertToEntity() {
        ChangeBank changeBank = new ChangeBank();
        changeBank.setExporterNTN(exporterNTN);
        changeBank.setExporterName(exporterName);
        changeBank.setExporterName(exportPaymentMode);
        changeBank.setExportPaymentMode(paymentIdentificationNo);
        changeBank.setCreatedOn(ZonedDateTime.now());
        return changeBank;
    }

    @Override
    public void convertToDTO(ChangeBank entity, boolean partialFill) {

    }

    @Override
    public ChangeBankDTO convertToNewDTO(ChangeBank entity, boolean partialFill) {
        return null;
    }
}
