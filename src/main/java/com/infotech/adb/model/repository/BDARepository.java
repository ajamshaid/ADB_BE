package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.BDA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BDARepository extends JpaRepository<BDA, Long> {
    List<BDA> findAllByOrderByLastModifiedDateDesc();

}
