package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "CONTAINER_TRUCK_NUMBER", length = 25 ,nullable = true)
    private String containerOrTruckNumber;

    @Column(name = "CONTAINER_TYPE",length = 10, nullable = true)
    private String containerType;

    @Column(name = "SEAL_NUMBER",length = 20, nullable = true)
    private String sealNumber;

    /*
     * Entity Specific Fields
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="GD_ID", nullable=true)
    private GD gd;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="GD_EXPORT_ID", nullable=true)
    private GDExport gdExport;
}
