package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.SettelmentOfFI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SettlementOfFIRepository extends JpaRepository<SettelmentOfFI, Long> {

}
