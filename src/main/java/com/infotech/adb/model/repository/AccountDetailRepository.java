package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {
   AccountDetail findByIban(String iban);
}
