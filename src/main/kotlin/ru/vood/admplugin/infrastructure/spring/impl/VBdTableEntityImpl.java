package ru.vood.admplugin.infrastructure.spring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.CommonFunctionService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdTableEntityService;
import ru.vood.admplugin.infrastructure.spring.repository.VBdTableEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service//("jpaVBdTableEntityService")
@Repository
@Transactional
public class VBdTableEntityImpl /*extends VBdObjectEntityImpl/*ParentForAllImpl*/ implements VBdTableEntityService {

    protected EntityManager em;
    private VBdTableEntityRepository bdTableEntityRepository;
    private CommonFunctionService commonFunction;

    @Autowired
    public VBdTableEntityImpl(EntityManager em, VBdTableEntityRepository bdTableEntityRepository, CommonFunctionService commonFunction) {
        this.em = em;
        this.bdTableEntityRepository = bdTableEntityRepository;
        this.commonFunction = commonFunction;
    }

    @Override
    public VBdTableEntity save(VBdTableEntity entity) {
        return bdTableEntityRepository.save(entity);
    }

    @Override
    public void delete(VBdTableEntity entity) {
        bdTableEntityRepository.delete(entity);
    }

    @Override
    public List<VBdTableEntity> findByTypeObjectCodeIn(String... codeS) {
        List<String> codeList = new ArrayList<>();
        Collections.addAll(codeList, codeS);

        Query query = em.createQuery("" +
                "   select a1 from VBdTableEntity a1 " +
                "   join fetch a1.parent " +
                "   join fetch a1.typeObject " +
                "   where a1.typeObject.code in :codeTypeS " +
                " ")
                .setParameter("codeTypeS", codeList);
        return (List) (ArrayList<VBdTableEntity>) query.getResultList();//bdTableEntityRepository.findByTypeObjectCodeIn(codeS);
        //return bdTableEntityRepository.findByTypeObjectCodeIn(codeS);
    }

    @Override
    public List<VBdTableEntity> findByParent(VBdObjectEntity parent) {
        return bdTableEntityRepository.findByParent(parent);
    }

    @Override
    public VBdTableEntity findByCode(String code) throws CoreExeption {
        Query query = em.createQuery("select a1 from VBdTableEntity a1 " +
                //"  join fetch a1.typeObject a2 " + //" on a1.code in :codeTypeS  " +
                " join fetch a1.parent " +
                " join fetch a1.typeObject " +
//                " join fetch a1.parent " +
                " where a1.code = :codeTypeS " +
                //        " order by a2.id "
                "")
                .setParameter("codeTypeS", code);


        //List list = bdTableEntityRepository.findByCode(code);
        List list1 = (ArrayList<VBdTableEntity>) query.getResultList();
        commonFunction.checkOn(list1);
        return (VBdTableEntity) list1.get(0);
    }

    /*
    public ParentForAll findOne(BigDecimal bigDecimal) {
        return bdTableEntityRepository.findOne(bigDecimal);
    }

    public boolean exists(BigDecimal bigDecimal) {
        return bdTableEntityRepository.exists(bigDecimal);
    }

    public long count() {
        return bdTableEntityRepository.count();
    }

    public void delete(Iterable<? extends ParentForAll> iterable) {
        bdTableEntityRepository.delete((Iterable<? extends VBdTableEntity>) iterable);
    }


    public VBdTableEntity save(ParentForAll parentForAll) {
        return bdTableEntityRepository.save((VBdTableEntity) parentForAll);
    }

    public void delete(ParentForAll parentForAll) {
        bdTableEntityRepository.delete((VBdTableEntity) parentForAll);
    }
*/
//------------------------------


}
