package com.infotech.adb.model.entity;

import com.opencsv.bean.CsvBindByName;
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

    @CsvBindByName(column = "Bank Code")
    @Column(name = "BANK_CODE",length = 14, nullable=true)
    private String bankCode;

    @CsvBindByName(column = "Restricted Countries(IM)")
    @Column(name = "Restricted_Countries_Import")
    @Lob
    private String restrictedCountriesForImport;

    @CsvBindByName(column = "Restricted Countries(EX)")
    @Column(name = "Restricted_Countries_EXPORT")
    @Lob
    private String restrictedCountriesForExport;

    @CsvBindByName(column = "Restricted Commodities(IM)")
    @Column(name = "Restricted_Commodities_Import")
    @Lob
    private String restrictedCommoditiesForImport;

    @CsvBindByName(column = "Restricted Commodities(EX)")
    @Column(name = "Restricted_Commodities_Export")
    @Lob
    private String restrictedCommoditiesForExport;

    @CsvBindByName(column = "Restricted Suppliers(IM)")
    @Column(name = "Restricted_Suppliers_Import")
    @Lob
    private String restrictedSuppliersForImport;

    @CsvBindByName(column = "Restricted Suppliers(EX)")
    @Column(name = "Restricted_Suppliers_Export")
    @Lob
    private String restrictedSuppliersForExport;

}