package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.GDFinancial;
import com.infotech.adb.model.entity.LogRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GDFinancialRepository extends JpaRepository<GDFinancial, Long> {

    GDFinancial findByGdNumber(String gdNumber);
}
