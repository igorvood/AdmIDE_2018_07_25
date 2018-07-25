package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexedColumnsEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdIndexedColumnsEntityRepository extends CrudRepository<VBdIndexedColumnsEntity, BigDecimal> {

    List<VBdIndexedColumnsEntity> findByCollectionId(BigDecimal collectionId);

    List<VBdIndexedColumnsEntity> findByCollectionIdIn(List<BigDecimal> collectionId);
}
