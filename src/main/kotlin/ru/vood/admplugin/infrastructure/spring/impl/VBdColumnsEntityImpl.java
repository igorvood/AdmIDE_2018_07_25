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
import ru.vood.admplugin.infrastructure.spring.repository.VBdColomnsEntityRepository;
import ru.vood.admplugin.infrastructure.spring.repository.VBdTableEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service//("jpaVBdColomnsEntityService")
@Repository
@Transactional
public class VBdColumnsEntityImpl /*extends VBdObjectEntityImpl /*ParentForAllImpl*/ implements VBdColumnsEntityService {
    protected EntityManager em;
    private VBdColomnsEntityRepository bdColomnsEntityRepository;
    private CommonFunctionService commonFunctionService;
    private VBdTableEntityRepository vBdTableEntityRepository;

    @Autowired
    public VBdColumnsEntityImpl(EntityManager em, VBdColomnsEntityRepository bdColomnsEntityRepository, CommonFunctionService commonFunctionService, VBdTableEntityRepository vBdTableEntityRepository) {
        this.em = em;
        this.bdColomnsEntityRepository = bdColomnsEntityRepository;
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
        List list = query.getResultList();
        return list;
    }

    @Override
    public List<VBdColumnsEntity> findAllByParent(VBdTableEntity parent) {
        List<VBdTableEntity> vBdTableEntities = new ArrayList<>();
        vBdTableEntities.add(parent);
        VBdTableEntity oneTable = vBdTableEntityRepository.findById(parent.getParent().getId()).get();
        while (oneTable != null) {
            vBdTableEntities.add(oneTable);
            oneTable = vBdTableEntityRepository.findById(oneTable.getParent().getId()).orElse(null);
        }
        Query query = em.createQuery("select a2 from VBdColumnsEntity a2 " +
                "  join fetch a2.typeObject a1 " +
                "  join fetch a2.parent a3  " +
                "  join fetch a2.typeValue a5 " +
                "  join fetch a5.typeObject a6 " +
                " where a2.parent in :parent " +
                " order by a2.id ")
                .setParameter("parent", vBdTableEntities);
        List list = query.getResultList();
        return list;
    }

    @Override
    public VBdColumnsEntity save(VBdColumnsEntity entity) {
        return bdColomnsEntityRepository.save(entity);
    }

    @Override
    public void delete(VBdColumnsEntity entity) {
        bdColomnsEntityRepository.delete(entity);
    }

    @Override
    public VBdColumnsEntity findColomn(VBdTableEntity parent, String code) throws CoreExeption {
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
}