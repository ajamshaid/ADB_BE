package com.infotech.adb.model.repository;



import com.infotech.adb.model.entity.MqLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface MqLogRepository extends JpaRepository<MqLog, Long> {

    List<MqLog> findAllByOrderByIdDesc();

    @Query("from MqLog r where r.message like %:message% and r.msgType like :msgType% and (r.dateTime between :fromDate and :toDate) order by r.dateTime desc")
    List<MqLog> searchLogs(@Param("message") String iban, @Param("msgType") String msgId
            , @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
