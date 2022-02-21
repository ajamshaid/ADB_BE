package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.MqLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

    List<FinancialTransaction> findByTypeAndStatusOrderByLastModifiedDateDesc(String type, String status);

    @Modifying
    @Query("update FinancialTransaction en set en.status = :status where en.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Query("from FinancialTransaction r where r.type = :type and r.iban like %:iban% and r.name like %:name% and (r.lastModifiedDate between :fromDate and :toDate) order by r.lastModifiedBy desc")
    List<FinancialTransaction> searchFT(@Param("type") String type, @Param("iban") String iban, @Param("name") String name
            , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
