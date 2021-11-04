package com.infotech.adb.model.repository;

import com.infotech.adb.model.entity.BDA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BDARepository extends JpaRepository<BDA, Long> {
    List<BDA> findAllByOrderByLastModifiedDateDesc();

    @Modifying
    @Query("update BDA en set en.status = :status where en.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
