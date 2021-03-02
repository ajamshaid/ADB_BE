package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.LogRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LogRequestRepository extends JpaRepository<LogRequest, Long> {

}
