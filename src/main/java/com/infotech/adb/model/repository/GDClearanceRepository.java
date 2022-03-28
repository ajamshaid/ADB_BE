package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.GDClearance;
import com.infotech.adb.model.entity.ReversalOfBdaBca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface GDClearanceRepository extends JpaRepository<GDClearance, Long> {

    List<GDClearance> findAllByOrderByLastModifiedDateDesc();

    @Modifying
    @Query("update GDClearance en set en.status = :status where en.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Query("from GDClearance r where r.status in (:statuses) and r.tradeType like %:tradeType% and r.traderNTN like %:traderNTN% and (r.lastModifiedDate between :fromDate and :toDate) order by r.lastModifiedDate desc , r.lastModifiedBy desc")
    List<GDClearance> searchGDClearance(@Param("tradeType") String tradeType, @Param("traderNTN") String traderNTN
            , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate , @Param("statuses") List<String> statuses);
}
