package com.infotech.adb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "GD_CONTAINER_VEHICLE_INFO")
@Getter
@Setter
public class GDContainerVehicleInfo {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONTAINER_TRUCK_NUMBER", length = 25 ,nullable = false)
    private String containerOrTruckNumber;

    @Column(name = "CONTAINER_TYPE",length = 10, nullable = false)
    private String containerType;

    @Column(name = "SEAL_NUMBER",length = 20, nullable = false)
    private String sealNumber;

    /*
     * Entity Specific Fields
     */
    @ManyToOne
    @JoinColumn(name="GD_ID", nullable=false)
    private GD gd;

}