package ru.vood.admplugin.infrastructure.spring.entity;

import javax.persistence.*;

import static ru.vood.admplugin.infrastructure.spring.entity.ParentForAll.SCHEMA;

@Entity
@Table(name = "V_BD_TABLE", schema = SCHEMA, catalog = "")
public class VBdTableEntity extends VBdObjectEntity {

    @Basic
    @Column(name = "TABLE_SPACE", nullable = true, length = 50)
    private String tableSpace;

    @Basic
    @Column(name = "STORAGE", nullable = true, length = 500)
    private String storage;

    @Basic
    @Column(name = "LENGTH", nullable = true, precision = 0)
    private Long length;

    @Basic
    @Column(name = "PRECISION", nullable = true, precision = 0)
    private Long precision;

    @ManyToOne
    @JoinColumn(name = "TO_TYPE", referencedColumnName = "ID", nullable = true)
    private VBdTableEntity toType;

    public String getTableSpace() {
        return tableSpace;
    }

    public void setTableSpace(String tableSpace) {
        this.tableSpace = tableSpace;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(Long precision) {
        this.precision = precision;
    }

    public VBdTableEntity getToType() {
        return toType;
    }

    public void setToType(VBdTableEntity toType) {
        this.toType = toType;
    }


}
