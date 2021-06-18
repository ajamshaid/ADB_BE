package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BCA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BCARepository extends JpaRepository<BCA, Long> {

}
