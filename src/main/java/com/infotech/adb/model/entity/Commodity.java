package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COMMODITY")
@Getter
@Setter
public class Commodity {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

}