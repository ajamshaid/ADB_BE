package com.infotech.adb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ITEM_INFORMATION")
@Getter
@Setter
public class ItemInformation {

    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HS_CODE",length = 14, nullable = false)
    private String hsCode;

    @Column(name = "GOODS_DESCRIPTION",length = 1000, nullable = true)
    private String goodsDescription;

    @Column(name = "QUANTITY", precision=24, scale=4)
    private BigDecimal quantity;

    @Column(name = "UOM",length = 10, nullable = true)
    private String uom;

    @Column(name = "COUNTRY_OF_ORIGIN",length = 3, nullable = true)
    private String countryOfOrigin;

    @Column(name = "SAMPLE",length = 3, nullable = true)
    private String sample;

    @Column(name = "SAMPLE_VALUE",length = 20, nullable = true)
    private BigDecimal sampleValue;

    @Column(name = "UNIT_PRICE", precision=24, scale=4)
    private BigDecimal unitPrice;

    @Column(name = "TOTAL_VALUE", precision=24, scale=4)
    private BigDecimal totalValue;

    @Column(name = "IMPORT_OR_EXPORT_VALUE", precision=24, scale=4)
    private BigDecimal importOrExportValue;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="FIN_TRANSACTION_ID", nullable=true)
    private FinancialTransaction financialTransaction;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="GD_EXPORT_ID", nullable=true)
    private GDExport gdExport;

    @Override
    public String toString() {
        return "ItemInformation{" +
                "id=" + id +
                ", hsCode='" + hsCode + '\'' +
                ", goodsDescription='" + goodsDescription + '\'' +
                ", quantity=" + quantity +
                ", uom='" + uom + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", sample='" + sample + '\'' +
                ", sampleValue='" + sampleValue + '\'' +
                ", unitPrice=" + unitPrice +
                ", importOrExportValue=" + importOrExportValue +
                '}';
    }
}
