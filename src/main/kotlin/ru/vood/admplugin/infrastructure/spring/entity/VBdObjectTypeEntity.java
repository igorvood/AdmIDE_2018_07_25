package ru.vood.admplugin.infrastructure.spring.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

import static ru.vood.admplugin.infrastructure.spring.entity.ParentForAll.SCHEMA;

@Entity
@Table(name = "V_BD_OBJECT_TYPE", schema = SCHEMA, catalog = "")
public class VBdObjectTypeEntity extends ParentForAll {
    @Id
    @GenericGenerator(name = "seqId", strategy = "ru.vood.admplugin.infrastructure.spring.entity.GeneratorId")
    @GeneratedValue(generator = "seqId")

/*    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqId")
    @SequenceGenerator(name = "seqId", sequenceName = "SEQ_ID", allocationSize = 10)*/
    @Column(name = "ID", nullable = false, precision = 0)
    private BigDecimal id;

    @Basic
    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Basic
    @Column(name = "NAME", nullable = false, length = 250)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT", referencedColumnName = "ID")
    private VBdObjectTypeEntity parent;

    @Basic
    @Column(name = "NEED_DDL")
    private boolean needDDL;


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public VBdObjectTypeEntity getParent() {
        return parent;
    }

    public void setParent(VBdObjectTypeEntity parent) {
        this.parent = parent;
    }

    public boolean isNeedDDL() {
        return needDDL;
    }

    public void setNeedDDL(boolean needDDL) {
        this.needDDL = needDDL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VBdObjectTypeEntity)) return false;

        VBdObjectTypeEntity that = (VBdObjectTypeEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "VBdObjectTypeEntity{" +
                "id=" + this.getId() +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", vBdObjectTypeByParent=" + parent +
                '}';
    }

}
