package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESTRICTED_COUNTRIES")
@Getter
@Setter
public class RestrictedCountries {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", nullable=false)
    private AccountDetail accountDetail;

    @ManyToOne
    @JoinColumn(name="COUNTRY_ID", nullable=false)
    private Country country;

    @Column(name = "TYPE", nullable=false)
    private String type;

}
