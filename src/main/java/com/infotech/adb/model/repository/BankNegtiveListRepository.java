package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BankNegativeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankNegtiveListRepository extends JpaRepository<BankNegativeList, Long> {
   BankNegativeList findDistinctByBankCode(String bankCode);
}
