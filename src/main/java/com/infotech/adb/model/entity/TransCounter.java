package com.infotech.adb.model.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Trans_Counters")
@Data
public class TransCounter {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BANK_CODE",length = 14, nullable=true)
    private String bankCode;

    @Column(name = "IMP")
     private Integer imp;

    @Column(name = "EXP")
     private Integer exp;

    @Column(name = "BCA")
      private Integer bca;

    @Column(name = "BDA")
      private Integer bda;

    @Column(name = "COB")
     private Integer cob;

}