package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORIZED_PAYMENT_MODES")
@Getter
@Setter
public class AuthorizedPaymentModes {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE", nullable=false)
    private String code;

    @Column(name = "TYPE" , nullable=false)
    private String type;

    /*
     * Entity Specific Fields
     */
    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", nullable=false)
    private AccountDetail accountDetail;
}
