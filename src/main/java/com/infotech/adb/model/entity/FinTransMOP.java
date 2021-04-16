package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "FIN_TRANS_MOP")
@Getter
@Setter
public class FinTransMOP {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_TRANSACTION_ID", nullable = false)
    private FinancialTransaction financialTransaction;

    @Column(name = "MODE_OF_PAYMENT",length = 3, nullable = false)
    private String modeOfPayment;
}
