package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.ItemInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemInformationRepository extends JpaRepository<ItemInformation, Long> {

    List<ItemInformation> findAllByFinancialTransaction_Id(Long id);

}
