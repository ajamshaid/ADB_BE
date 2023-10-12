package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.COBGdFt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface COBGdFtRepository extends JpaRepository<COBGdFt, Long> {

    List<COBGdFt> findAllByOrderByLastModifiedDateDesc();

    @Modifying
    @Query("UPDATE COBGdFt cob SET cob.cobStatus = :cobStatus , cob.status = :status WHERE cob.id = :id")
    void updateCOBGdFtStatus(@Param("cobStatus") String cobStatus, @Param("status") String status, @Param("id") Long id);
}
