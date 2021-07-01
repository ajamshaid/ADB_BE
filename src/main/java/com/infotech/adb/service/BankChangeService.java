package com.infotech.adb.service;

import com.infotech.adb.model.entity.ChangeOfBank;
import com.infotech.adb.model.repository.ChangeBankRepository;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional(value = "masterTransactionManager", rollbackFor = {Throwable.class})
@Log4j2
public class BankChangeService {
/*
    @Autowired
    private ChangeBankRepository changeBankRepository;

    public List<ChangeOfBank> getAllChangeBanks(Boolean isSuspended) {
        log.info("getAllChangeBanks method called..");
        if (AppUtility.isEmpty(isSuspended)) {
            return changeBankRepository.findAll();
        } else {
            return changeBankRepository.findAll();
        }
    }

    public Optional<ChangeOfBank> getChangeBankById(Long id) {
        log.info("getChangeBankById method called..");
        if (!AppUtility.isEmpty(id)) {
            return changeBankRepository.findById(id);
        }
        return Optional.empty();
    }

//    public ChangeOfBank getChangeBankByExporterNTN(String exporterNTN) {
//        log.info("getChangeBankById method called..");
//        if (!AppUtility.isEmpty(exporterNTN)) {
//            return changeBankRepository.findByExporterNTN(exporterNTN);
//        }
//        return null;
//    }

    public ChangeOfBank createChangeBank(ChangeOfBank changeBank) {
        log.info("createChangeBank method called..");
        return changeBankRepository.save(changeBank);
    }

    public ChangeOfBank updateChangeBank(ChangeOfBank changeBank) {
        log.info("updateChangeBank method called..");
        return changeBankRepository.save(changeBank);
    }

    public void deleteChangeBankById(Long id) {
        log.info("deleteChangeBankById method called..");

        changeBankRepository.deleteById(id);
    }


 */
}

