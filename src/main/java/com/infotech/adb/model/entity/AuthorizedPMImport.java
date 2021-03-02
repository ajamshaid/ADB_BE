package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORIZED_PM_IMPORT")
@Getter
@Setter
public class AuthorizedPMImport {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    /*
     * Entity Specific Fields
     */
    @ManyToOne
    @JoinColumn(name="ACCOUNT_DETAIL_ID", nullable=false)
    private AccountDetail accountDetail;
}
