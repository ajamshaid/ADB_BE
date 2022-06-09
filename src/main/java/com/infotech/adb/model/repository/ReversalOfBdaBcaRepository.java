package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.CancellationOfFT;
import com.infotech.adb.model.entity.ReversalOfBdaBca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ReversalOfBdaBcaRepository extends JpaRepository<ReversalOfBdaBca, Long> {

    List<ReversalOfBdaBca> findAllByOrderByLastModifiedDateDesc();

    @Query("from ReversalOfBdaBca r where r.status in (:statuses) and r.tradeType like %:tradeType% and r.finInsUniqueNumber like %:finInsUniqueNumber% and r.traderNTN like %:traderNTN% and (r.lastModifiedDate between :fromDate and :toDate) order by r.lastModifiedDate desc , r.lastModifiedBy desc")
    List<ReversalOfBdaBca> searchReversalOfBDABCA(@Param("tradeType") String tradeType, @Param("traderNTN") String traderNTN, @Param("finInsUniqueNumber") String finInsUniqueNumber
            , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("statuses") List<String> statuses);

}
