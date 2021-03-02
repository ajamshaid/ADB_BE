package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

   Optional<AccountDetail> findByIbanAndEmailAndMobileNumber(String iban, String emailAddress, String mobileNumber);

   AccountDetail findByIban(String iban);
}
