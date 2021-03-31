package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT_DETAIL")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AccountDetail extends BaseEntity {

    @Column(name = "IBAN", unique = true, nullable = false)
    private String iban;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "ACCT_TITLE")
    private String accountTitle;
    @Column(name = "ACCT_NUMBER")
    private String accountNumber;
    @Column(name = "ACCT_STATUS")
    private String accountStatus;
    @Column(name = "ACCT_TYPE")
    private String accountType;
    @Column(name = "NTN")
    private String ntn;
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
}