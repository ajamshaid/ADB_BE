package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

}
