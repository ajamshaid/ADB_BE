package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.GDExport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface GDExportRepository extends JpaRepository<GDExport, Long> {

    @Query("from GDExport r where r.consigneeName like %:name% and r.gdNumber like %:gdnumber% and r.ntnFtn like %:ntnFtn% and (r.lastModifiedDate between :fromDate and :toDate) order by r.lastModifiedDate desc , r.lastModifiedBy desc")
    List<GDExport> searchGDExport(@Param("name") String name, @Param("gdnumber") String gdNumber,
                                  @Param("ntnFtn") String ntnFtn, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
