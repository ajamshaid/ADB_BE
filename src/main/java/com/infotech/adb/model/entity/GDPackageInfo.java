package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "GD_PACKAGE")
@Getter
@Setter
public class GDPackageInfo {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMBER_OF_PACKAGES", precision=19, scale=4, nullable = false)
    private BigDecimal numberOfPackages;

    @Column(name = "PACKAGE_TYPE",length = 20, nullable = false)
    private String packageType;

    @Column(name = "NET_WEIGHT",length = 20, nullable = false)
    private String netWeight;


    @Column(name = "GROSS_WEIGHT",length = 20, nullable = false)
    private String grossWeight;

    /*
     * Entity Specific Fields
     */
    @ManyToOne
    @JoinColumn(name="GD_ID", nullable=false)
    private GD gd;

}
