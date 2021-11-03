package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

    List<FinancialTransaction> findByType(String type);

    @Modifying
    @Query("update FinancialTransaction ft set ft.status = :status where ft.id = :id")
    int updateFTStatus(@Param("id") Long id, @Param("status") String status);

}
