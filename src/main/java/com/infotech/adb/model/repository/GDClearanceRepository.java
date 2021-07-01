package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.GDClearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GDClearanceRepository extends JpaRepository<GDClearance, Long> {

}
