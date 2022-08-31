package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.GD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface GDRepository extends JpaRepository<GD, Long> {

    @Query("from GD r where (r.financialTransaction.finInsUniqueNumber like %:finInsUniqueNumber% or r.financialTransaction.finInsUniqueNumber is null) and r.consigneeName like %:name% and r.gdNumber like %:gdnumber% and r.ntnFtn like %:ntnFtn% and (r.lastModifiedDate between :fromDate and :toDate) order by r.lastModifiedDate desc , r.lastModifiedBy desc")
    List<GD> searchGD(@Param("finInsUniqueNumber") String finInsUniqueNumber, @Param("name") String name, @Param("gdnumber") String gdNumber,
                      @Param("ntnFtn") String ntnFtn, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}