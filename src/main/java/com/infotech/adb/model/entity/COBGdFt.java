package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "COB_GD_FT")
@Getter
@Setter
public class COBGdFt {
    /*
     * Key Fields
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COB_UNIQUE_ID_NUMBER")
    private String cobUniqueIdNumber;

    @Column(name = "TRADE_TYPE")
    private String tradeTranType;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="FT_ID", nullable=false )
    private FinancialTransaction ft;


    // Import GD and BDA
    @OneToMany(mappedBy = "cobGdFt", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<GD> gdSet;

    @OneToMany(mappedBy = "cobGdFt", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<BDA> bdaSet;

    // Export GD and BCA
    @OneToMany(mappedBy = "cobGdFt", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<GDExport> gdExportSet;

    @OneToMany(mappedBy = "cobGdFt", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<BCA> bcaSet;

}
