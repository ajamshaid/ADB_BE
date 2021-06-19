package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "NUMBER_OF_PACKAGES", precision=19, scale=4, nullable = true)
    private BigDecimal numberOfPackages;

    @Column(name = "PACKAGE_TYPE",length = 20, nullable = true)
    private String packageType;

    @Column(name = "NET_WEIGHT",length = 20, nullable = true)
    private String netWeight;


    @Column(name = "GROSS_WEIGHT",length = 20, nullable = true)
    private String grossWeight;

    /*
     * Entity Specific Fields
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="GD_ID", nullable=false)
    private GD gd;

}
