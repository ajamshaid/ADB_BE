package com.infotech.adb.service;

import com.infotech.adb.model.entity.GDFinancial;
import com.infotech.adb.model.repository.FinancialTransactionRepository;
import com.infotech.adb.model.repository.GDFinancialRepository;
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
public class GDFinancialService {

    @Autowired
    private GDFinancialRepository gdFinancialRepository;

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    public List<GDFinancial> getAllGDFinancials(Boolean isSuspended) {
        log.info("getAllGDFinancials method called..");
        if (AppUtility.isEmpty(isSuspended)) {
            return gdFinancialRepository.findAll();
        } else {
            return gdFinancialRepository.findAll();
        }
    }

    public Optional<GDFinancial> getGDFinancialById(Long id) {
        log.info("getGDFinancialById method called..");
        if (!AppUtility.isEmpty(id)) {
            return gdFinancialRepository.findById(id);
        }
        return Optional.empty();
    }

    public GDFinancial getGDFinancialByGDNumber(String gdNumber) {
        log.info("getGDFinancialById method called..");
        if (!AppUtility.isEmpty(gdNumber)) {
            return gdFinancialRepository.findByGdNumber(gdNumber);
        }
        return null;
    }

    public GDFinancial getGDFinancialByGDNumber(String gdNumber, String gdType) {
        log.info("getGDFinancialById method called..");
        if (!AppUtility.isEmpty(gdNumber)) {
            return gdFinancialRepository.findByGdNumberAndGdType(gdNumber, gdType);
        }
        return null;
    }

    public GDFinancial createGDFinancial(GDFinancial gdFinancial) {
        log.info("createGDFinancial method called..");
        return gdFinancialRepository.save(gdFinancial);
    }

    public GDFinancial updateGDFinancial(GDFinancial gdFinancial) {
        log.info("updateGDFinancial method called..");
        gdFinancial.setUpdatedOn(ZonedDateTime.now());
        return gdFinancialRepository.save(gdFinancial);
    }

    public void deleteGDFinancialById(Long id) {
        log.info("deleteGDFinancialById method called..");

        gdFinancialRepository.deleteById(id);
    }
}

