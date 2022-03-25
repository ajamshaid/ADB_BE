package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.BDA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BDARepository extends JpaRepository<BDA, Long> {
    List<BDA> findAllByOrderByLastModifiedDateDesc();

    @Modifying
    @Query("update BDA en set en.status = :status where en.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Query("from BDA r where r.status in (:statuses) and r.iban like %:iban% and r.importerName like %:name% and (r.lastModifiedDate between :fromDate and :toDate) and r.finInsUniqueNumber like %:finInsUniqueNumber% order by lastModifiedDate desc , r.lastModifiedBy desc")
    List<BDA> searchBDA(@Param("iban") String iban, @Param("name") String name
            , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate , @Param("statuses") List<String> statuses , @Param("finInsUniqueNumber") String finInsUniqueNumber);

}
