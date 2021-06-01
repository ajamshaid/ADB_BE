package com.infotech.adb.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Bank_Negative_List")
@Data
public class BankNegativeList {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BANK_CODE",length = 14, nullable=false)
    private String bankCode;

    @Column(name = "Restricted_Countries_Import")
    private String restrictedCountriesForImport;

    @Column(name = "Restricted_Countries_EXPORT")
    private String restrictedCountriesForExport;

    @Column(name = "Restricted_Commodities_Import")
    private String restrictedCommoditiesForImport;

    @Column(name = "Restricted_Commodities_Export")
    private String restrictedCommoditiesForExport;

    @Column(name = "Restricted_Suppliers_Import")
    private String restrictedSuppliersForImport;

    @Column(name = "Restricted_Suppliers_Export")
    private String restrictedSuppliersForExport;

}