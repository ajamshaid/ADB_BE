package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.LogRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface LogRequestRepository extends JpaRepository<LogRequest, Long> {

    List<LogRequest> findAllByOrderByIdDesc();

    @Query("from LogRequest r where r.requestPayload like %:iban% and r.msgIdentifier like :msgId% and (r.requestTime between :fromDate and :toDate) order by r.responseTime desc")
    List<LogRequest> searchLogs(@Param("iban") String iban, @Param("msgId") String msgId
    , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
