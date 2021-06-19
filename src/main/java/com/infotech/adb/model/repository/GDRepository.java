package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.GD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GDRepository extends JpaRepository<GD, Long> {

}
