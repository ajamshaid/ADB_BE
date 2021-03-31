package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

   AccountDetail findByIbanAndEmailAndMobileNumberAndNtn(String iban, String emailAddress, String mobileNumber,String ntn);

   @Query("select case when count(a)> 0 then true else false end from AccountDetail a " +
           " where lower(a.iban) = lower(:iban) and lower(a.email) = lower(:emailAddress)" +
           " and lower(a.mobileNumber) = lower(:mobileNumber)  and lower(a.ntn) = lower(:ntn) ")
   boolean isExistAccountDetail(@Param("iban") String iban
           ,@Param("emailAddress") String emailAddress
           , @Param("mobileNumber") String mobileNumber
           ,@Param("ntn") String ntn);

   AccountDetail findByIban(String iban);
}
