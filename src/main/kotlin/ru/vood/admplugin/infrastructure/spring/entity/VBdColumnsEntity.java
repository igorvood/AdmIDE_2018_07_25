package ru.vood.admplugin.infrastructure.spring.entity;

import javax.persistence.*;

import static ru.vood.admplugin.infrastructure.spring.entity.ParentForAll.SCHEMA;

@Entity
@Table(name = "V_BD_COLOMNS", schema = SCHEMA, catalog = "")
public class VBdColumnsEntity extends VBdObjectEntity {

    @Basic
    @Column(name = "NOT_NULL")
    private boolean notNull = false;

    @ManyToOne
    @JoinColumn(name = "TYPE_COLOMN", referencedColumnName = "ID", nullable = true)
    private VBdObjectTypeEntity typeColumn;

    @ManyToOne
    @JoinColumn(name = "TYPE_VALUE", referencedColumnName = "ID", nullable = true)
    private VBdObjectEntity typeValue;

    public boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public VBdObjectTypeEntity getTypeColumn() {
        return typeColumn;
    }

    public void setTypeColumn(VBdObjectTypeEntity typeColumn) {
        this.typeColumn = typeColumn;
    }

    public VBdObjectEntity getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(VBdObjectEntity typeValue) {
        this.typeValue = typeValue;
    }


}
