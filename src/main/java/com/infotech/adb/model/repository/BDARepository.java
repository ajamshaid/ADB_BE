package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BDA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BDARepository extends JpaRepository<BDA, Long> {

}
