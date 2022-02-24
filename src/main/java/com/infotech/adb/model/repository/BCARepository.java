package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BCARepository extends JpaRepository<BCA, Long> {

    List<BCA> findAllByOrderByLastModifiedDateDesc();


    @Modifying
    @Query("update BCA en set en.status = :status where en.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Query("from BCA r where r.status = :status and r.iban like %:iban% and r.exporterName like %:name% and (r.lastModifiedDate between :fromDate and :toDate) order by r.lastModifiedDate desc , r.lastModifiedBy desc")
    List<BCA> searchBCA(@Param("iban") String iban, @Param("name") String name
            , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate , @Param("status") String status);

}
