package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.BankNegativeList;
import com.infotech.adb.model.entity.TransCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransCounterRepository extends JpaRepository<TransCounter, Long> {
    TransCounter findDistinctByBankCode(String bankCode);
}
