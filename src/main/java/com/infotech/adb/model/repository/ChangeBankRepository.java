package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.ChangeBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChangeBankRepository extends JpaRepository<ChangeBank, Long> {

    ChangeBank findByExporterNTN(String gdNumber);
}
