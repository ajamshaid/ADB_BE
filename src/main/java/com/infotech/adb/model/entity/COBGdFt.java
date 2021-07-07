package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COB_GD_FT_INFO")
@Getter
@Setter
public class COBGdFt {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COB_UNIQUE_ID_NUMBER")
    private String cobUniqueIdNumber;

    @Column(name = "TRADE_TYPE")
    private String tradeTranType;

    @OneToOne
    @JoinColumn(name="FT_ID")
    private FinancialTransaction ft;

    @OneToOne
    @JoinColumn(name="GD_ID")
    private GD gd;

    @OneToOne
    @JoinColumn(name="GD_EXPORT_ID")
    private GDExport gdExport;

    @OneToOne
    @JoinColumn(name="BDA_ID")
    private BDA bda;

    @OneToOne
    @JoinColumn(name="BCA_ID")
    private BCA bca;
}
