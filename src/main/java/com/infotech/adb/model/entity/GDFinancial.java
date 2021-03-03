package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "GD_FINANCIAL")
@Getter
@Setter
public class GDFinancial extends BaseEntity {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GD_NUMBER",unique=true)
    private String gdNumber;

    @Column(name = "GD_OBJECT", length = 4000)
    private String gdfObjectJson;
}
