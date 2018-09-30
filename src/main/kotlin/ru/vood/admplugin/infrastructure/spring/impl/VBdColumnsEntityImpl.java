package ru.vood.admplugin.infrastructure.spring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.CommonFunctionService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdColumnsEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.repository.VBdColumnsEntityRepository;
import ru.vood.admplugin.infrastructure.spring.repository.VBdTableEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
@Repository
@Transactional
public class VBdColumnsEntityImpl /*extends VBdObjectEntityImpl /*ParentForAllImpl*/ implements VBdColumnsEntityService {
    protected EntityManager em;
    private VBdColumnsEntityRepository bdColumnsEntityRepository;
    private CommonFunctionService commonFunctionService;
    private VBdTableEntityRepository vBdTableEntityRepository;

    @Autowired
    public VBdColumnsEntityImpl(EntityManager em, VBdColumnsEntityRepository bdColumnsEntityRepository, CommonFunctionService commonFunctionService, VBdTableEntityRepository vBdTableEntityRepository) {
        this.em = em;
        this.bdColumnsEntityRepository = bdColumnsEntityRepository;
        this.commonFunctionService = commonFunctionService;
        this.vBdTableEntityRepository = vBdTableEntityRepository;
    }

    @Override
    public List<VBdColumnsEntity> findByParent(VBdTableEntity parent) {
        Query query = em.createQuery("select a2 from VBdColumnsEntity a2 " +
                "  join fetch a2.typeObject a1 " +
                "  join fetch a2.parent a3  " +
                "  join fetch a2.typeValue a5 " +
                "  join fetch a5.typeObject a6 " +
                " where a2.parent = :parent " +
                " order by a2.id ")
                .setParameter("parent", parent);
        return query.getResultList();
    }

    @Override
    public List<VBdColumnsEntity> findAllByParent(VBdTableEntity parent) {
        List<VBdTableEntity> vBdTableEntities = new ArrayList<>();
        vBdTableEntities.add(parent);
        VBdTableEntity oneTable = vBdTableEntityRepository.findById(parent.getParent().getId());//.get();
        while (oneTable != null) {
            vBdTableEntities.add(oneTable);
            oneTable = vBdTableEntityRepository.findById(oneTable.getParent().getId());//.orElse(null);
        }
        Query query = em.createQuery("select a2 from VBdColumnsEntity a2 " +
                "  join fetch a2.typeObject a1 " +
                "  join fetch a2.parent a3  " +
                "  join fetch a2.typeValue a5 " +
                "  join fetch a5.typeObject a6 " +
                " where a2.parent in :parent " +
                " order by a2.id ")
                .setParameter("parent", vBdTableEntities);
        return query.getResultList();
    }

    @Override
    public VBdColumnsEntity save(VBdColumnsEntity entity) {
        return bdColumnsEntityRepository.save(entity);
    }

    @Override
    public void delete(VBdColumnsEntity entity) {
        bdColumnsEntityRepository.delete(entity);
    }

    @Override
    public VBdColumnsEntity findColumn(VBdTableEntity parent, String code) throws CoreExeption {
        Query query = em.createQuery("select a1 from VBdColumnsEntity a1 " +
                "  join fetch a1.parent a3  " +
                " where a1.parent = :parent " +
                " and a1.code = :code" +
                " ")
                .setParameter("parent", parent)
                .setParameter("code", code);

        List list = query.getResultList();
        commonFunctionService.checkOn(list);
        return (VBdColumnsEntity) list.get(0);
    }

    @Override
    public List<VBdColumnsEntity> findColumnRefIn(VBdTableEntity entity) {
/*
        Query query = em.createQuery("select a1 from VBdColumnsEntity a1, VBdTableEntity tab " +
                " where a1.typeValue.typeObject = :refType " +
                " and a1.typeValue = tab" +
                " and tab.toType = :entity ")
                .setParameter("refType", ObjectTypes.getREFERENCE())
                .setParameter("entity", entity);
*/
        Query query = em.createQuery("select a1 from VBdColumnsEntity a1" +
                " join VBdTableEntity tab on a1.typeValue = tab  and tab.toType = :entity " +
                " join fetch a1.parent a3 " +
                " where a1.typeValue.typeObject = :refType")
                .setParameter("refType", ObjectTypes.getREFERENCE())
                .setParameter("entity", entity);

        return query.getResultList();
    }
}