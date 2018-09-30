package ru.vood.admplugin.infrastructure.spring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexedColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdIndexedColumnsEntityService;
import ru.vood.admplugin.infrastructure.spring.repository.VBdIndexedColumnsEntityRepository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@Service
@Repository
@Transactional
public class VBdIndexedColumnsImpl implements VBdIndexedColumnsEntityService {


    protected EntityManager em;

    private VBdIndexedColumnsEntityRepository bdColumnsEntityRepository;

    @Autowired
    public VBdIndexedColumnsImpl(EntityManager em, VBdIndexedColumnsEntityRepository bdColumnsEntityRepository) {
        this.em = em;
        this.bdColumnsEntityRepository = bdColumnsEntityRepository;
    }

    @Override
    public VBdIndexedColumnsEntity save(VBdIndexedColumnsEntity entity) {
        return bdColumnsEntityRepository.save(entity);
    }

    @Override
    public void delete(VBdIndexedColumnsEntity entity) {
        bdColumnsEntityRepository.delete(entity);
    }

    @Override
    public List<VBdIndexedColumnsEntity> findByCollectionId(BigDecimal collectionId) {
        return bdColumnsEntityRepository.findByCollectionId(collectionId);
    }

    @Override
    public List<VBdIndexedColumnsEntity> findByCollectionIdIn(List<BigDecimal> collectionId) {
        /*Query query = em.createQuery("select a1 from VBdIndexedColumnsEntity a1 " +

                " where a1.collectionId in :collectionId " +
                //" order by a2.id " +
                "")
                .setParameter("collectionId", collectionId);
        List list = query.getResultList();*/
        return bdColumnsEntityRepository.findByCollectionIdIn(collectionId);
    }
}
