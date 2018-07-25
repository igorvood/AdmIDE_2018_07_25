package ru.vood.admplugin.infrastructure.spring.intf;

import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexedColumnsEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdIndexedColumnsEntityService {

    VBdIndexedColumnsEntity save(VBdIndexedColumnsEntity entity);

    void delete(VBdIndexedColumnsEntity entity);

    List<VBdIndexedColumnsEntity> findByCollectionId(BigDecimal collectionId);

    List<VBdIndexedColumnsEntity> findByCollectionIdIn(List<BigDecimal> collectionId);
}
