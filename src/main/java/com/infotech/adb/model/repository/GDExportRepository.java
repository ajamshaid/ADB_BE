package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.GDExport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GDExportRepository extends JpaRepository<GDExport, Long> {

}
