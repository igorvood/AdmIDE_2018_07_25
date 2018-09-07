package ru.vood.admplugin.infrastructure.spring.entity;

import org.hibernate.annotations.GenericGenerator;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad.LIndexedColumns;

import javax.persistence.*;
import java.math.BigDecimal;

import static ru.vood.admplugin.infrastructure.applicationConst.Const.COLLECTION;
import static ru.vood.admplugin.infrastructure.spring.entity.ParentForAll.SCHEMA;


@Entity
@Table(name = LIndexedColumns.tableName, schema = SCHEMA, catalog = "")
public class VBdIndexedColumnsEntity {
    @Id
    @GenericGenerator(name = "seqId", strategy = "ru.vood.admplugin.infrastructure.spring.entity.GeneratorId")
    @GeneratedValue(generator = "seqId")
    @Column(name = "ID", nullable = false, precision = 0)
    private BigDecimal id;

    @Basic
    @Column(name = COLLECTION)
    private BigDecimal collectionId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COLUMN_REF", referencedColumnName = "ID", nullable = false)
    private VBdColumnsEntity columnRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLLECTION, referencedColumnName = "COLUMNS", insertable = false, updatable = false)
    private VBdIndexEntity indexEntity;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(BigDecimal collectionId) {
        this.collectionId = collectionId;
    }

    public VBdColumnsEntity getColumnRef() {
        return columnRef;
    }

    public void setColumnRef(VBdColumnsEntity columnRef) {
        this.columnRef = columnRef;
    }
}
