package ru.vood.admplugin.infrastructure.spring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexedColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.CommonFunctionService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdIndexEntityService;
import ru.vood.admplugin.infrastructure.spring.repository.VBdIndexEntityRepository;
import ru.vood.admplugin.infrastructure.spring.repository.VBdIndexedColumnsEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Repository
@Transactional
public class VBdIndexEntityImp implements VBdIndexEntityService {

    @Autowired
    protected VBdIndexEntityRepository entityTestRepository;
    @Autowired
    protected VBdIndexedColumnsEntityRepository indexedColomnsEntityRepository;
    @Autowired
    protected EntityManager em;
    @Autowired
    private CommonFunctionService commonFunctionService;

    @Override
    public VBdIndexEntity save(VBdIndexEntity entity) {
        if (entity.getColomnsEntities() != null) {
            entity.getColomnsEntities().stream().forEach(col -> indexedColomnsEntityRepository.save(col));
        }
        return entityTestRepository.save(entity);
    }

    @Override
    public void delete(VBdIndexEntity entity) {
        if (entity.getColomnsEntities() != null) {
            entity.getColomnsEntities().stream().forEach(col -> indexedColomnsEntityRepository.delete(col));
        }
        entityTestRepository.delete(entity);
    }

    @Override
    public List<VBdIndexEntity> findByParent(VBdObjectEntity parent) {
        Query query = em.createQuery("select a1 from VBdIndexEntity a1 " +
                "  join fetch a1.typeObject a2 " +
                "  join fetch a1.parent a3  " +
//                "  join fetch a1.typeValue a5 " +
                "  join fetch a1.typeObject a6 " +
                // "  left join VBdIndexedColumnsEntity a7 on  a1.columns= a7.collectionId " +
                //"  join fetch a1.colomnsEntities a7 " +
                " where a1.parent = :parent " +
                //" order by a2.id " +
                "")
                .setParameter("parent", parent);
        List<VBdIndexEntity> listVBdIndexEntity = query.getResultList();
        List<BigDecimal> bigDecimals = listVBdIndexEntity.stream().map(q -> q.getColumns()).collect(Collectors.toList());
        List<VBdIndexedColumnsEntity> indexedColomnsEntities = indexedColomnsEntityRepository.findByCollectionIdIn(bigDecimals);
        for (VBdIndexEntity li : listVBdIndexEntity) {
            for (VBdIndexedColumnsEntity col : indexedColomnsEntities) {
                if (li.getColumns().equals(col.getCollectionId())) {
                    li.addColomn(col);
                }
            }
        }
        return listVBdIndexEntity;
    }

    @Override
    public VBdIndexEntity findByCode(String code) throws CoreExeption {
        List<VBdIndexEntity> indexEntities = entityTestRepository.findByCode(code);
        return (VBdIndexEntity) commonFunctionService.checkOn(indexEntities);
    }
}
