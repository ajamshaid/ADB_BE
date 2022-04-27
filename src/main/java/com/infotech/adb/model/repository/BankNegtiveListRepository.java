package com.infotech.adb.model.repository;


import com.infotech.adb.model.entity.BankNegativeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface BankNegtiveListRepository extends JpaRepository<BankNegativeList, Long> {
    BankNegativeList findDistinctByBankCode(String bankCode);

    @Modifying
    @Query("update BankNegativeList bn set bn.restrictedCountriesForExport = :countriesExport ,bn.restrictedCountriesForImport = :countriesImport where bn.id = :id")
    int updateNegativeBankListCountries(@Param("countriesExport") String countriesExport, @Param("countriesImport") String countriesImport , @Param("id") Long id);

    @Query("update BankNegativeList bn set bn.restrictedCommoditiesForExport = :commoditiesExport , bn.restrictedCommoditiesForImport = :commoditiesImport where bn.id = :id")
    int updateNegativeBankListCommodities(@Param("commoditiesExport") String commoditiesExport, @Param("commoditiesImport") String commoditiesImport , @Param("id") Long id);

    @Query("update BankNegativeList bn set bn.restrictedSuppliersForExport = :suppliersExport ,bn.restrictedSuppliersForImport = :suppliersImport where bn.id = :id")
    int updateNegativeBankListSuppliers(@Param("suppliersExport") String suppliersExport, @Param("suppliersImport") String suppliersImport , @Param("id") Long id);
}
