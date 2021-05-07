package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT_DETAIL")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccountDetail extends BaseEntity {


    @CsvBindByName(column = "\uFEFFIBAN")
  //  @CsvBindByPosition(position = 0)
    @Column(name = "IBAN", unique = true, nullable = false)
    private String iban;

    @CsvBindByName(column = "Email")
    @Column(name = "EMAIL")
    private String email;

    @CsvBindByName(column = "MobileNumber")
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @CsvBindByName(column = "Account Title")
    @Column(name = "ACCT_TITLE")
    private String accountTitle;

    @CsvBindByName(column = "Account Number")
    @Column(name = "ACCT_NUMBER")
    private String accountNumber;

    @CsvBindByName(column = "Account Status")
    @Column(name = "ACCT_STATUS")
    private String accountStatus;

    @CsvBindByName(column = "Account Type")
    @Column(name = "ACCT_TYPE")
    private String accountType;

    @CsvBindByName(column = "NTN")
    @Column(name = "NTN")
    private String ntn;

    @CsvBindByName(column = "CNIC")
    @Column(name = "CNIC")
    private String cnic;


    @OneToMany(mappedBy = "accountDetail", fetch = FetchType.LAZY)
    private Set<AuthorizedPaymentModes> authorizedPaymentModesSet;

    @OneToMany(mappedBy = "accountDetail", fetch = FetchType.LAZY)
    private Set<RestrictedCountries> restrictedCountriesSet;

    @OneToMany(mappedBy = "accountDetail", fetch = FetchType.LAZY)
    private Set<RestrictedCommodities> restrictedCommoditiesSet;

    @OneToMany(mappedBy = "accountDetail", fetch = FetchType.LAZY)
    private Set<RestrictedSuppliers> restrictedSuppliersSet;


    @CsvBindByName(column = "AuthPM(IM)")
    private String AuthPMImport;
    @CsvBindByName(column = "AuthPM(EX)")
    private String AuthPMExport;
    @CsvBindByName(column = "Restricted Countries(IM)")
    private String restrictedCountriesImport;
    @CsvBindByName(column = "Restricted Countries(EX)")
    private String restrictedCountriesExport;

}