package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESTRICTED_COMMODITIES")
@Getter
@Setter
public class RestrictedCommodities {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE",length = 14, nullable=false)
    private String code;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", nullable=false)
    private AccountDetail accountDetail;

    @Column(name = "TYPE", nullable=false)
    private String type;
}