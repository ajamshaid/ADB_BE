package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infotech.adb.util.AppConstants;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_DETAIL"
        ,uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"IBAN"}
                ,name= AppConstants.DBConstraints.UNIQ_IBAN)}
)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccountDetail {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName(column = "IBAN")
  //  @CsvBindByPosition(position = 0)
    @Column(name = "IBAN",nullable = false)
    private String iban;

    @CsvBindByName(column = "Account Status")
    @Column(name = "ACCT_STATUS")
    private String accountStatus;

    @CsvBindByName(column = "AuthPM(IM)")
    @Column(name = "AUTH_PM_IMPORT")
    private String AuthPMImport;

    @CsvBindByName(column = "AuthPM(EX)")
    @Column(name = "AUTH_PM_EXPORT")
    private String AuthPMExport;

}