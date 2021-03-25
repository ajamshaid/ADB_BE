package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CHANGE_BANK")
@Getter
@Setter
public class ChangeBank extends BaseEntity {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EXPORTER_NTN",unique=true)
    private String exporterNTN;
    @Column(name = "EXPORTER_NAME")
    private String exporterName;
    @Column(name = "EXPORTER_PAYMENT_MODE")
    private String exportPaymentMode;
    @Column(name = "PAYMENT_IDENTIFICATION_NO",unique=true)
    private String paymentIdentificationNo;
}
