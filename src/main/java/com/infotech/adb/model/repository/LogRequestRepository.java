package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.LogRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface LogRequestRepository extends JpaRepository<LogRequest, Long> {

    List<LogRequest> findAllByOrderByIdDesc();

    @Query(" from LogRequest r where r.requestPayload like %:iban% order by r.responseTime desc")
    List<LogRequest> searchLogs(@Param("iban") String iban);

}
