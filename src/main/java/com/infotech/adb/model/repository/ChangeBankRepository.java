package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.ChangeOfBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChangeBankRepository extends JpaRepository<ChangeOfBank, Long> {

}
