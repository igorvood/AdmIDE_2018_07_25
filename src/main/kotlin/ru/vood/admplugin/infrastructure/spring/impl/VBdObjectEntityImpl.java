package ru.vood.admplugin.infrastructure.spring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.CommonFunctionService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.repository.VBdObjectEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service//("jpaVBdObjectEntityService")
@Repository
@Transactional
public class VBdObjectEntityImpl /*extends ParentForAllImpl*/ implements VBdObjectEntityService {


    protected EntityManager em;
    protected VBdObjectEntityRepository vBdObjectEntityRepository;
    private CommonFunctionService commonFunctionService;

    @Autowired
    public VBdObjectEntityImpl(EntityManager em, VBdObjectEntityRepository vBdObjectEntityRepository, CommonFunctionService commonFunctionService) {
        this.em = em;
        this.vBdObjectEntityRepository = vBdObjectEntityRepository;
        this.commonFunctionService = commonFunctionService;
    }

    //@Cacheable("referenceCache")
    public ArrayList<VBdObjectEntity> findByTypeObjectCodeIn(String... codeS) {

        List<String> stringList = new ArrayList<>(codeS.length);
        Collections.addAll(stringList, codeS);
        Query query = em.createQuery("select a2 from VBdObjectEntity a2 " +
                "  join fetch a2.typeObject a1 " + //" on a1.code in :codeTypeS  " +
                "  left join a2.parent a3  " +
                " where a1.code in :codeTypeS " +
                " order by a2.id ")
                .setParameter("codeTypeS", stringList);

        List list1 = (ArrayList<VBdObjectEntity>) query.getResultList();
        return (ArrayList<VBdObjectEntity>) list1;
    }

    @Override
    public List<VBdObjectEntity> findByParent(VBdObjectEntity parent) {
        return vBdObjectEntityRepository.findByParent(parent);
    }

    @Override
    //@CacheEvict("referenceCache")
    public VBdObjectEntity save(VBdObjectEntity entity) {
        if (entity.getDateCreate() == null) {
            entity.setDateCreate(new Date());
        }
        return vBdObjectEntityRepository.save(entity);
    }

    @Override
    //@CacheEvict("referenceCache")
    public void delete(VBdObjectEntity entity) {
        vBdObjectEntityRepository.delete(entity);
    }

    @Override
    public VBdObjectEntity findOne(BigDecimal id) {
        Query query = em.createQuery("select a2 from VBdObjectEntity a2 " +
                "  join fetch a2.typeObject a1 " + //" on a1.code in :codeTypeS  " +
//                "  left join a2.parent a3  " +
                " where a2.id = :idd " +
                //" order by a2.id " +
                "")
                .setParameter("idd", id);
        List list1 = (ArrayList<VBdObjectEntity>) query.getResultList();
        return (VBdObjectEntity) list1.get(0);
//        return vBdObjectEntityRepository.findOne(id);
    }

    @Override
    public List<VBdObjectEntity> findByParentAndTypeObject(VBdObjectEntity parent, VBdObjectTypeEntity objectTypeEntity) {
        Query query = em.createQuery("select a1 from VBdObjectEntity a1 " +
                //"  join VBdObjectEntity a2 on a2 = :parent " +
                //"  join VBdObjectTypeEntity a3 on a3 = :objectTypeEntity " +
                " where a1.parent = :parent " +
                " and a1.typeObject = :objectType" +
                //" order by a2.id " +
                " ")
                .setParameter("parent", parent)
                .setParameter("objectType", objectTypeEntity);
        return (List) (ArrayList<VBdObjectEntity>) query.getResultList();
    }

    @Override
    public VBdObjectEntity findByCode(String code) throws CoreExeption {
        List list = vBdObjectEntityRepository.findByCodeAndTypeObject(code, ObjectTypes.getOBJECT());
        return (VBdObjectEntity) commonFunctionService.checkOn(list);
    }

    @Override
    public VBdObjectEntity findByCodeAndParenCode(String code, String parentCode) throws CoreExeption {
        Query query = em.createQuery("select a1 from VBdObjectEntity a1" +
                " left join VBdObjectEntity a2 on a1.parent = a2 " + // and a2.code = :parentCode" +
                " join fetch a1.parent  " +
                " where a1.code = :code " +
                " and (a2.code = :parentCode or (:parentCode is null and  a2 is null ))" +
                //" order by a2.id " +
                " ")
                .setParameter("code", code)
                .setParameter("parentCode", parentCode);
        List list1 = (ArrayList<VBdObjectEntity>) query.getResultList();

//        try {
//            commonFunctionService.checkOn(list1);
//        } catch (CoreExeption e) {
//            e.printStackTrace();
//        }

        return (VBdObjectEntity) commonFunctionService.checkOn(list1);
    }

    @Override
    public List<VBdObjectEntity> findAll() {
        return vBdObjectEntityRepository.findAll();
    }
}
