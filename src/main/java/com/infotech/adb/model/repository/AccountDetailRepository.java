package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {
   AccountDetail findByIban(String iban);

   @Modifying
   @Query(value= "update AccountDetail ad set ad.AuthPMImport=:authPMImp, ad.AuthPMExport=:authPMExp  WHERE ad.iban = :iban" )
   public void updateAuthPMByIBAN(@Param("iban")String iban, @Param("authPMImp") String authPMImp,@Param("authPMExp") String authPMExp);

   @Modifying
   @Query(value= "update AccountDetail ad set ad.accountStatus=:status WHERE ad.iban = :iban" )
   public void updateStatusByIBAN(@Param("iban")String iban, @Param("status") String status);

}
