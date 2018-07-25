package ru.vood.admplugin.infrastructure.spring.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static ru.vood.admplugin.infrastructure.spring.entity.ParentForAll.SCHEMA;

@Entity
@Table(name = "V_BD_OBJECT", schema = SCHEMA, catalog = "")
@Inheritance(strategy = InheritanceType.JOINED)
public class VBdObjectEntity extends ParentForAll {

    @Id
    @GenericGenerator(name = "seqId", strategy = "ru.vood.admplugin.infrastructure.spring.entity.GeneratorId")
    @GeneratedValue(generator = "seqId")
    //@SequenceGenerator(name = "seqId", sequenceName = "SEQ_ID", allocationSize = 10)
    @Column(name = "ID", nullable = false, precision = 0)
    private BigDecimal id;

    @Basic
    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Basic
    @Column(name = "NAME", nullable = false, length = 250)
    private String name;

    @Basic
    @Column(name = "JAVA_CLASS", nullable = false, length = 512)
    private String javaClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT", referencedColumnName = "ID")
    private VBdObjectEntity parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TYPE_OBJECT", referencedColumnName = "ID", nullable = false)
    private VBdObjectTypeEntity typeObject;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CREATE", nullable = true, insertable = true, updatable = true)
    private Date dateCreate;

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

    public VBdObjectEntity getParent() {
        return parent;
    }

    public void setParent(VBdObjectEntity parent) {
        this.parent = parent;
    }

    public VBdObjectTypeEntity getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(VBdObjectTypeEntity typeObject) {
        this.typeObject = typeObject;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VBdObjectEntity)) return false;

        VBdObjectEntity that = (VBdObjectEntity) o;
        if (getId() == null) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public String toString() {
        return name;
        /*return "VBdObjectEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", javaClass='" + javaClass + '\'' +
                ", vBdObjectByParent=" + parent +
                ", typeObject=" + typeObject +
                '}';*/
    }
}
