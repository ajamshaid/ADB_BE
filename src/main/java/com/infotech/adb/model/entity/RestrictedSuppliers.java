package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESTRICTED_SUPPLIERS")
@Getter
@Setter
public class RestrictedSuppliers {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME"  , length = 100, nullable=false)
    private String NAME;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", nullable=false)
    private AccountDetail accountDetail;

    @Column(name = "TYPE", nullable=false)
    private String type;
}
