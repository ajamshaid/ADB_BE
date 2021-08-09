package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.SettelmentOfFI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SettlementOfFIRepository extends JpaRepository<SettelmentOfFI, Long> {

    List<SettelmentOfFI> findAllByOrderByLastModifiedDateDesc();

}
